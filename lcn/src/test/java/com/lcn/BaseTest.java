package com.lcn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

@ContextConfiguration("classPath*:/spring-mybatis.xml")
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests{
	public Logger logger = LoggerFactory.getLogger(getClass());
}
