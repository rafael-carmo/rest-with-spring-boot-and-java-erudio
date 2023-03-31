package br.com.erudio.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.mapper.custom.PersonMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

/*
 * serve para que o SpringBoot encare esse classe como um objeto que vai ser 
 * injetado em runtime em outras classes da aplicação.
 */
@Service
public class PersonServices {
	
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	@Autowired
	PersonMapper mapper;
	
	public List<PersonVO> findAll() {
		logger.info("Finding all people");
		return DozerMapper.parseListObjects(repository.findAll(), PersonVO.class) ;
	}
	
	public PersonVO findById(Long id) {
		logger.info("Finding one person!");
		
		PersonVO person = new PersonVO();
		person.setFirstName("Leandro");
		person.setLastName("Costa");
		person.setAddress("UBerlandia - Minas Gerais - Brasil");
		person.setGender("Male");
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		return DozerMapper.parseObject(entity, PersonVO.class);
	}
	
	public PersonVO create(PersonVO personVo) {
		logger.info("Creating one person!");
		
		var entity = DozerMapper.parseObject(personVo, Person.class);
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class); 
		
		return vo;
	}
	
	public PersonVOV2 createV2(PersonVOV2 personVoV2) {
		logger.info("Creating one person V2!");
		
		var entity = mapper.convertVoTOEntity(personVoV2);
		var vo = mapper.convertEntityToVo(repository.save(entity)); 
		
		return vo;
	}
	
	public PersonVO update(PersonVO person) {
		logger.info("Updating one person!");
		
		var entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class); 
		
		return vo;
	}
	
	public void delete(Long id) {
		logger.info("Deleting one person!");
		
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
	}
	
}
