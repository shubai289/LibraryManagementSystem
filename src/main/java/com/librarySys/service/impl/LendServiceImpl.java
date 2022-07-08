package com.librarySys.service.impl;

import com.librarySys.dao.BookMapper;
import com.librarySys.dao.LendMapper;
import com.librarySys.pojo.Book;
import com.librarySys.pojo.Lend;
import com.librarySys.pojo.User;
import com.librarySys.service.LendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LendServiceImpl implements LendService {
    @Resource
    private LendMapper lendMapper;

    @Resource
    private BookMapper bookMapper;

    @Override
    public List<Lend> showLendList() {
        return lendMapper.showAllLendList();
    }

    @Override
    public List<Lend> showUserLendList(User user) {
        return lendMapper.showUserLendList(user);
    }

    @Override
    public List<Lend> showBookLendList(Map<String, Integer> map) {
        return lendMapper.showBookLendList(map);
    }

    @Override
    public int insLendList(HashMap<String, Object> map) {
        int index = lendMapper.insLendList(map);
        if (index > 0) {
            //更新书籍状态，1为借出，2为在馆
            int i = bookMapper.updBookState(1, (Integer) map.get("book_id"));
            if (i > 0) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public int returnBook(int lend_id) {
        Lend lend = lendMapper.selById(lend_id);
        //判断书籍是否已归还,(1 , "未还"), (2 , "已还"), (3 , "逾期")
        if (lend.getState() == 2) {
            return 1;
        }
        //更新书籍状态，1为借出，2为在馆
        bookMapper.updBookState(2,lend.getBook_id());
        HashMap<String, Object> map = new HashMap<>();
        Date backDate = new Date();
        map.put("back_date",backDate);
        map.put("state", 2);
        map.put("id",lend.getId());
        return lendMapper.updLendList(map);
    }

}
