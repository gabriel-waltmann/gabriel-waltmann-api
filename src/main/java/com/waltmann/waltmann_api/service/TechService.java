package com.waltmann.waltmann_api.service;

import com.waltmann.waltmann_api.domain.tech.Tech;
import org.springframework.stereotype.Service;

@Service
public class TechService {
    public String retrieves() {
        return "retrieves";
    }

    public String retrievesOne(String id) {
        return "retrieves one" + id;
    }

    public String create(Tech tech) {
        return tech.getName();
    }

    public String update(String id, Tech tech) {
        return "update: " + id + " " + tech.getName();
    }

    public String delete(String id) {
        return "delete: " + id;
    }
}
