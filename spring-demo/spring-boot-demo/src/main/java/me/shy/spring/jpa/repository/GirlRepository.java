/**
 * @Since: 2019-12-07 14:57:22
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-12-07 16:31:35
 */
package me.shy.spring.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.shy.spring.jpa.entity.Girl;

public interface GirlRepository extends JpaRepository<Girl, Long> {

    List<Girl> findByAge(int age);

}
