package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.erudio.controllers.BookController;
import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BookRepository;

/*
 * serve para que o SpringBoot encare esse classe como um objeto que vai ser 
 * injetado em runtime em outras classes da aplicação.
 */
@Service
public class BookServices {
	
	private Logger logger = Logger.getLogger(BookServices.class.getName());
	
	@Autowired
	BookRepository repository;
	
	public List<BookVO> findAll() {
		logger.info("Finding all people");
		var books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
		books.stream().forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
		return books;
	}
	
	public BookVO findById(Long id) {
		logger.info("Finding one book!");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		var vo = DozerMapper.parseObject(entity, BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		
		
		/*vo.add(linkTo(methodOn(BookController.class).delete(id)).
				withRel("delete").
				withType("DELETE")
				);*/
		
		return vo;
	}
	
	public BookVO create(BookVO bookVo) {
		logger.info("Creating one book!");
		
		if(bookVo == null) throw new RequiredObjectIsNullException();
		
		var entity = DozerMapper.parseObject(bookVo, Book.class);
		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	public BookVO update(BookVO bookVo) {
		logger.info("Updating one book!");
		
		if(bookVo == null) throw new RequiredObjectIsNullException();
		
		var entity = repository.findById(bookVo.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		entity.setAuthor(bookVo.getAuthor());
		entity.setLaunchDate(bookVo.getLaunchDate());
		entity.setPrice(bookVo.getPrice());
		entity.setTitle(bookVo.getTitle());
		
		var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
		vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	public void delete(Long id) {
		logger.info("Deleting one book!");
		
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}
	
}
