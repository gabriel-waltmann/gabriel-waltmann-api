package com.waltmann.waltmann_api.service.user.link;

import com.waltmann.waltmann_api.domain.link.Link;
import com.waltmann.waltmann_api.domain.user.User;
import com.waltmann.waltmann_api.domain.user.link.UserLink;
import com.waltmann.waltmann_api.repositories.user.UserRepository;
import com.waltmann.waltmann_api.repositories.user.link.UserLinkRepository;
import com.waltmann.waltmann_api.service.link.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserLinkService {
    @Autowired
    private UserLinkRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LinkService linkService;

    public UserLink create(UUID userId, String name, String key) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Link link = linkService.create(name, key);

        UserLink userLink = new UserLink();

        userLink.setUser(user);

        userLink.setLink(link);

        repository.save(userLink);

        return userLink;
    }

    public List<UserLink> retrieves(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return repository.findByUserId(userId);
    }

    public UserLink retrievesOne(UUID userId, UUID userLinkId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return repository.findById(userLinkId)
                .orElseThrow(() -> new RuntimeException("User link not found"));
    }

    public void delete(UUID userId, UUID userLinkId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        repository.findById(userLinkId)
                .orElseThrow(() -> new RuntimeException("User link not found"));

        repository.deleteById(userLinkId);
    }

}
