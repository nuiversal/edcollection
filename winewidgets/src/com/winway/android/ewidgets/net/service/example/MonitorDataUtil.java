package com.winway.android.ewidgets.net.service.example;

import java.util.ArrayList;
import java.util.Random;

import com.google.gson.Gson;
import com.winway.android.ewidgets.R;
import com.winway.android.ewidgets.tree.entity.ItemEntity;
import com.winway.android.ewidgets.tree.entity.LevelEntity;
import com.winway.android.ewidgets.tree.utils.ImageURIUtil;

public class MonitorDataUtil {
	static class User {
		private String name;
		private String sex;
		private int age;
		private String from;
		private String idcard;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSex() {
			return sex;
		}

		public void setSex(String sex) {
			this.sex = sex;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}

		public String getIdcard() {
			return idcard;
		}

		public void setIdcard(String idcard) {
			this.idcard = idcard;
		}
	}

	public static final String first = "http://localhost/first";
	public static final String second = "http://localhost/second";
	public static final String last = "http://localhost/last";

	static User getUser() {
		User user = new User();
		user.setAge(26);
		user.setFrom("China");
		user.setIdcard("450983732636524544183X");
		user.setName("ZhangJiaJie");
		user.setSex("man");
		return user;
	}

	static String getJsonString() {
		Gson g = new Gson();
		return g.toJson(getUser());
	}

	public static Random random = new Random();

	public static ItemEntity getItem(String url) {
		ItemEntity item = new ItemEntity();
		if (last.equals(url)) {
			item.setHasChild(false);
		} else {
			item.setHasChild(true);
		}
		item.setUrl(url);
		item.setEx(false);
		int i = random.nextInt(100);
		item.setShowCheckBox(true);
		if (i % 3 == 0) {
			item.setChecked(true);
			item.setImageURI(ImageURIUtil.createImageURI("" + R.drawable.ico_go,
					ImageURIUtil.RES_TYPE_DRAWABLE));
			item.setShowEX(true);
		} else if (i % 3 == 1) {
			item.setChecked(false);
			item.setImageURI(ImageURIUtil.createImageURI("" + R.drawable.locus_round_click,
					ImageURIUtil.RES_TYPE_DRAWABLE));
			item.setShowEX(false);
		} else {
			item.setShowCheckBox(false);
			item.setShowImage(false);
			item.setShowEX(true);
		}
		item.setText("AndroidTree - " + i);
		item.setJsonData(getJsonString());
		item.setObjData(getUser());
		return item;
	}

	public static LevelEntity getData(String url) {
		LevelEntity level = new LevelEntity();
		int sum = random.nextInt(8) + 1;
		ArrayList<ItemEntity> list = new ArrayList<>();
		for (int i = 0; i < sum; i++) {
			list.add(getItem(url));
		}
		level.setDatas(list);
		return level;
	}
}
