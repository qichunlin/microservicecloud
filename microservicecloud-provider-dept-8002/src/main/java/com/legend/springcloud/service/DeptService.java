package com.legend.springcloud.service;

import com.legend.springcloud.entity.Dept;

import java.util.List;


public interface DeptService {
	public boolean add(Dept dept);

	public Dept get(Long id);

	public List<Dept> list();
}
