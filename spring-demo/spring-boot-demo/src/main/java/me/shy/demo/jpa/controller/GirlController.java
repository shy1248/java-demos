/**
 * @Since: 2019-12-07 14:59:02
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-12-07 16:36:34
 */
package me.shy.demo.jpa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.shy.demo.jpa.entity.Girl;
import me.shy.demo.jpa.repository.GirlRepository;

@RestController
@RequestMapping(value = "/girls")
public class GirlController {

    @Autowired
    GirlRepository girlRepository;

    // insert
    @PostMapping
    public Girl add(@RequestParam(value = "cupSize", required = true) String cupSize,
            @RequestParam(value = "age", required = true) Integer age) {
        Girl girl = new Girl();
        girl.setCupSize(cupSize);
        girl.setAge(age);
        return girlRepository.save(girl);

    }

    // delete
    @DeleteMapping("{id}")
    public String remove(@PathVariable("id") Long id) {
        girlRepository.deleteById(id);
        return "ok";
    }

    // update
    @PutMapping("{id}")
    public Girl modify(@PathVariable("id") Long id, @RequestParam(value = "cupSize") String cupSize,
            @RequestParam(value = "age") Integer age) {
        Girl girl = new Girl();
        girl.setId(id);
        girl.setCupSize(cupSize);
        girl.setAge(age);
        girlRepository.save(girl);
        return girl;
    }

    // select one
    @GetMapping("{id}")
    public Optional<Girl> takeOne(@PathVariable("id") Long id) {
        return girlRepository.findById(id);
    }

    // select all
    @GetMapping
    public List<Girl> takeAll() {
        return girlRepository.findAll();
    }

    // select by age
    @GetMapping("age/{age}")
    public List<Girl> takeByAge(@PathVariable("age") int age) {
        return girlRepository.findByAge(age);
    }
}
