/**
 * @Since: 2019-12-07 20:32:39
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-12-07 21:46:09
 */
package me.shy.demo.web.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.shy.demo.web.domain.Girl;
import me.shy.demo.web.exception.GirlException;
import me.shy.demo.web.repository.GirlRepository;
import me.shy.demo.web.response.ResponseEnum;

@Component public class GirlService {
    @Autowired private GirlRepository girlRepository;

    public void save(Girl girl) throws Exception {
        int age = girl.getAge();
        if (age <= 12) {
            throw new GirlException(ResponseEnum.PRIMARY_SCHOOL);
        } else if (age >= 13 && age <= 16) {
            System.out.println("====================MIDDLE==============");
            throw new GirlException(ResponseEnum.MIDDLE_SCHOOL);
        } else {
            girl.setId(girl.getId());
            girl.setCupSize(girl.getCupSize());
            girl.setAge(girl.getAge());
            this.girlRepository.save(girl);
        }
    }

    public void forgetById(Long id) {
        this.girlRepository.deleteById(id);
    }

    public Optional<Girl> takeById(Long id) {
        return this.girlRepository.findById(id);
    }

    public List<Girl> takeByAge(int age) {
        return this.girlRepository.findByAge(age);
    }

    public List<Girl> takeAll() {
        return this.girlRepository.findAll();
    }

}
