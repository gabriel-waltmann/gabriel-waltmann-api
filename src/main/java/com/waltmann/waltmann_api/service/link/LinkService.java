package com.waltmann.waltmann_api.service.link;

import com.waltmann.waltmann_api.domain.link.Link;
import com.waltmann.waltmann_api.repositories.link.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LinkService {
  @Autowired
  private LinkRepository repository;

  public Link create(String name, String key) {
    Link link = new Link();

    link.setName(name);
    link.setKey(key);

    return repository.save(link);
  }

  public List<Link> retrieves(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);

    Page<Link> links = repository.findAll(pageable);

    return links.stream().toList();
  }

  public Link retrievesOne(UUID id) {
    return repository.findById(id).get();
  }

  public Link update(UUID id, String name, String key) {
    Link link = repository.findById(id).get();

    link.setName(name);
    link.setKey(key);

    repository.save(link);

    return link;
  }

  public Boolean delete(UUID id) {
    repository.deleteById(id);

    return true;
  }
}
