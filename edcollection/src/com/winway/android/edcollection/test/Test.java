package com.winway.android.edcollection.test;

import com.lidroid.xutils.exception.DbException;
import com.winway.android.db.xutils.BaseDBUtil;
import com.winway.android.edcollection.project.entity.EmMembersEntity;

import android.content.Context;

public class Test extends BaseDBUtil{
     public Test(Context context, String dbname) {
		super(context, dbname);
		// TODO Auto-generated constructor stub
	}

	public  void test() {
		EmMembersEntity em=new EmMembersEntity();
		em.setEmMembersId(5);
		em.setEmProjectId("111");
		em.setRoleId("222");
		em.setRoleName("name");
		em.setUserId("3333");
		em.setUserName("name");
		try {
			this.save(em);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
