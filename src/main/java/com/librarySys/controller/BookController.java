package com.librarySys.controller;

import com.librarySys.pojo.Book;
import com.librarySys.pojo.Page;
import com.librarySys.service.impl.BookServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class BookController {
    @Resource
    private BookServiceImpl bookServiceImpl;
//    @RequestMapping("/showAll")
//    public String showBooks(HttpServletRequest request, HttpServletResponse response) {
//        List<Book> list = bookServiceImpl.show();
//        request.setAttribute("bookList",list);
//        return "book_list";
//    }


    @RequestMapping("/toBookList")
    public ModelAndView toBookList(HttpServletRequest request){
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if(null==page || "".equals(page)){
            page="1";
        }
        if(null==rows || "".equals(rows)){
            rows="10";
        }
        ModelAndView mav = new ModelAndView("admin/bs_bookList");
        Page<Book> list = bookServiceImpl.findBookByPage(Integer.parseInt(page), Integer.parseInt(rows));
        mav.addObject("bookList",list);
        mav.addObject("msg","show");
        return mav;
    }

    @RequestMapping("/toBookOP")
    public String toBookOP(){
        return "admin/bs_bookop";
    }

    @RequestMapping("/showAll")
    public ModelAndView findByPage(HttpServletRequest request) {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        if(null==page || "".equals(page)){
            page="1";
        }
        if(null==rows || "".equals(rows)){
            rows="10";
        }
        ModelAndView mav = new ModelAndView("book_list");
        Page<Book> list = bookServiceImpl.findBookByPage(Integer.parseInt(page), Integer.parseInt(rows));
        mav.addObject("bookList",list);
        mav.addObject("msg","show");
        return mav;
    }

    @RequestMapping("/searchBook")
    public ModelAndView searchBook(HttpServletRequest request) {
        String entry = request.getParameter("entry");
        String p = request.getParameter("page");
        String r = request.getParameter("rows");
        Page<Book> list = new Page<>();
        if(null==r || "".equals(r)){
            r="10";
        }
        if(null==p || "".equals(p)){
            p="1";
        }
        int page = Integer.parseInt(p);
        if (page <= 1) {
            page = 1;
        }
        int rows = Integer.parseInt(r);
        int start;
        start = (page - 1) * 10;
        List<Book> books = bookServiceImpl.selBookByPageSearch(entry, start, rows);

        int totalCount = bookServiceImpl.SearchBookforCount(entry);
        //计算总页数
        int totalPage = totalCount % rows == 0 ? totalCount / rows : totalCount / rows + 1;
        list.setTotalPage(totalPage);
        list.setList(books);
        list.setTotalCount(totalCount);
        list.setCurrentPage(page);
        list.setRows(rows);

        ModelAndView mav = new ModelAndView("book_list");
        mav.addObject("msg","search");
        mav.addObject("bookList",list);
        mav.addObject("dEntry",entry);
        return mav;
    }

    @RequestMapping("/getBookInfo")
    public ModelAndView getBookInfo(HttpServletRequest request) {
        String b_id = request.getParameter("b_id");
        Book book = bookServiceImpl.selBookById(Integer.parseInt(b_id));

        ModelAndView mav = new ModelAndView("book_info");
        if (book != null) {
            mav.addObject("book",book);
        } else {
            mav.addObject("r_mav","找不到书籍信息");
        }
        return mav;
    }

    @RequestMapping(value="/doDownload")
    public String downloadResource(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

        String bId = request.getParameter("b_id");
        String dataDirectory = request.getServletContext().getRealPath("/res/data/");
        System.out.println(dataDirectory+bId);
        File file = new File(dataDirectory, bId+".pdf");
        if (file.exists()) {
            //设置响应类型，这里是下载pdf文件
            response.setContentType("application/pdf");
            //设置Content-Disposition，设置attachment，浏览器会激活文件下载框；filename指定下载后默认保存的文件名
            //不设置Content-Disposition的话，文件会在浏览器内打卡，比如txt、img文件
            response.addHeader("Content-Disposition","attachment; filename="+ bId+".pdf");
            byte[] buffer = new byte[4196];
            // if using Java 7, use try-with-resources
            try (FileInputStream fis = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(fis)) {
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (IOException ex) {
                // do something,
                // probably forward to an Error page
            }
        }
        return null;
    }

    @RequestMapping("/insertBook")
    public ModelAndView insertBook(Book newBook,@RequestParam("file") MultipartFile upload, HttpServletRequest request) throws IOException {
        String filename = upload.getOriginalFilename();
        String path = request.getSession().getServletContext().getRealPath("/res/data/bookcover/");
        newBook.setImage(filename);
        /**
         *文件上传
         */
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }

        if (upload != null && !upload.isEmpty()) {
            upload.transferTo(new File(path,filename));
        }

        ModelAndView mav = new ModelAndView("redirect:/toBookList");
        int state = 2;
        newBook.setState(state);
        boolean i = bookServiceImpl.insertNewBook(newBook);
        if(i){
            mav.addObject("mav", "添加成功!");
        }else {
            mav.addObject("r_mav", "添加失败,书籍已存在");
        }
        System.out.println("保存成功！");
        return mav;
    }

    @RequestMapping("/updateBookInfo")
    public ModelAndView updateBookInfo(Book book,@RequestParam("file") MultipartFile upload, HttpServletRequest request) throws IOException {
        String filename = upload.getOriginalFilename();
        System.out.println("-|"+filename+"|-");
        String path = request.getSession().getServletContext().getRealPath("/res/data/bookcover/");

        /**
         *文件上传
         */
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();

        }

        if (Objects.equals(filename,"") || filename==null)
        {
            Book b = bookServiceImpl.selBookById(book.getId());
            book.setImage(b.getImage());
        } else {
            book.setImage(filename);
        }
        if (!upload.isEmpty()) {
            upload.transferTo(new File(path,filename));
        }

        boolean i = bookServiceImpl.updateBookInfo(book);
        ModelAndView mav = new ModelAndView("redirect:/toBookList");
        if(i){
            mav.addObject("mav", "添加成功!");
        }else {
            mav.addObject("r_mav", "添加失败,书籍已存在");
        }
        System.out.println("保存成功！");
        return mav;
    }


    @RequestMapping("/updateBook")
    public ModelAndView updateBook(HttpServletRequest request){
        String bookId = request.getParameter("bookId");
        ModelAndView mav = new ModelAndView("admin/bs_bookop");
        Book book = bookServiceImpl.selBookById((Integer.parseInt(bookId)));

        mav.addObject("book",book);
        return mav;
    }

    @RequestMapping("/deleteBook")
    public ModelAndView deleteBook(HttpServletRequest request){
        String delBy = request.getParameter("bookId");
        String page = request.getParameter("page");
        int rows = 10;
        if(null==page || "".equals(page)){
            page="1";
        }
        ModelAndView mav = new ModelAndView("admin/bs_bookList");
        boolean isDelete = bookServiceImpl.delById(Integer.valueOf(delBy));
        if(isDelete) {
            mav.addObject("mav", "删除成功！");
            System.out.println("删除成功！");
        } else {
            mav.addObject("r_mav", "书籍不存在！");
        }

        Page<Book> list = bookServiceImpl.findBookByPage(Integer.parseInt(page), rows);
        mav.addObject("bookList",list);
        return mav;
    }

}