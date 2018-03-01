package com.winway.android.map.selectPoint.entity;

import java.util.List;

/**
 * json解析实体
 * 
 * @author lyh
 * @version 创建时间：2018年1月10日
 *
 */

public class GetJsonBaseEntity {

	private int status;
	private ResultBean result;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ResultBean getResult() {
		return result;
	}

	public void setResult(ResultBean result) {
		this.result = result;
	}

	public static class ResultBean {

		private LocationBean location;
		private String formatted_address;
		private String business;
		private AddressComponentBean addressComponent;
		private String sematic_description;
		private int cityCode;
		private List<PoisBean> pois;
		private List<?> roads;
		private List<?> poiRegions;

		public LocationBean getLocation() {
			return location;
		}

		public void setLocation(LocationBean location) {
			this.location = location;
		}

		public String getFormatted_address() {
			return formatted_address;
		}

		public void setFormatted_address(String formatted_address) {
			this.formatted_address = formatted_address;
		}

		public String getBusiness() {
			return business;
		}

		public void setBusiness(String business) {
			this.business = business;
		}

		public AddressComponentBean getAddressComponent() {
			return addressComponent;
		}

		public void setAddressComponent(AddressComponentBean addressComponent) {
			this.addressComponent = addressComponent;
		}

		public String getSematic_description() {
			return sematic_description;
		}

		public void setSematic_description(String sematic_description) {
			this.sematic_description = sematic_description;
		}

		public int getCityCode() {
			return cityCode;
		}

		public void setCityCode(int cityCode) {
			this.cityCode = cityCode;
		}

		public List<PoisBean> getPois() {
			return pois;
		}

		public void setPois(List<PoisBean> pois) {
			this.pois = pois;
		}

		public List<?> getRoads() {
			return roads;
		}

		public void setRoads(List<?> roads) {
			this.roads = roads;
		}

		public List<?> getPoiRegions() {
			return poiRegions;
		}

		public void setPoiRegions(List<?> poiRegions) {
			this.poiRegions = poiRegions;
		}

		public static class LocationBean {
			/**
			 * lng : 113.42953974540887 lat : 23.165719429900598
			 */

			private double lng;
			private double lat;

			public double getLng() {
				return lng;
			}

			public void setLng(double lng) {
				this.lng = lng;
			}

			public double getLat() {
				return lat;
			}

			public void setLat(double lat) {
				this.lat = lat;
			}
		}

		public static class AddressComponentBean {

			private String country;
			private int country_code;
			private String country_code_iso;
			private String country_code_iso2;
			private String province;
			private String city;
			private int city_level;
			private String district;
			private String town;
			private String adcode;
			private String street;
			private String street_number;
			private String direction;
			private String distance;

			public String getCountry() {
				return country;
			}

			public void setCountry(String country) {
				this.country = country;
			}

			public int getCountry_code() {
				return country_code;
			}

			public void setCountry_code(int country_code) {
				this.country_code = country_code;
			}

			public String getCountry_code_iso() {
				return country_code_iso;
			}

			public void setCountry_code_iso(String country_code_iso) {
				this.country_code_iso = country_code_iso;
			}

			public String getCountry_code_iso2() {
				return country_code_iso2;
			}

			public void setCountry_code_iso2(String country_code_iso2) {
				this.country_code_iso2 = country_code_iso2;
			}

			public String getProvince() {
				return province;
			}

			public void setProvince(String province) {
				this.province = province;
			}

			public String getCity() {
				return city;
			}

			public void setCity(String city) {
				this.city = city;
			}

			public int getCity_level() {
				return city_level;
			}

			public void setCity_level(int city_level) {
				this.city_level = city_level;
			}

			public String getDistrict() {
				return district;
			}

			public void setDistrict(String district) {
				this.district = district;
			}

			public String getTown() {
				return town;
			}

			public void setTown(String town) {
				this.town = town;
			}

			public String getAdcode() {
				return adcode;
			}

			public void setAdcode(String adcode) {
				this.adcode = adcode;
			}

			public String getStreet() {
				return street;
			}

			public void setStreet(String street) {
				this.street = street;
			}

			public String getStreet_number() {
				return street_number;
			}

			public void setStreet_number(String street_number) {
				this.street_number = street_number;
			}

			public String getDirection() {
				return direction;
			}

			public void setDirection(String direction) {
				this.direction = direction;
			}

			public String getDistance() {
				return distance;
			}

			public void setDistance(String distance) {
				this.distance = distance;
			}
		}

		public static class PoisBean {
			/**
			 * addr : 广州市天河区大观街639号 cp : direction : 北 distance : 339 name :
			 * 广东省食品药品职业技术学校图书馆 poiType : 教育培训 point :
			 * {"x":113.43064467740132,"y":23.163091382805522} tag : 教育培训;图书馆
			 * tel : uid : 4aba3093db3ed2846cc02c60 zip : parent_poi :
			 * {"name":"广东省食品药品职业技术学校"
			 * ,"tag":"教育培训;中学","addr":"广州市天河区大观街639号","point"
			 * :{"x":113.43179450845372
			 * ,"y":23.16309968930104},"direction":"西北","distance"
			 * :"403","uid":"c9eb3a6f7b0c5135a3acc479"}
			 */

			private String addr;
			private String cp;
			private String direction;
			private String distance;
			private String name;
			private String poiType;
			private PointBean point;
			private String tag;
			private String tel;
			private String uid;
			private String zip;
			private ParentPoiBean parent_poi;

			public String getAddr() {
				return addr;
			}

			public void setAddr(String addr) {
				this.addr = addr;
			}

			public String getCp() {
				return cp;
			}

			public void setCp(String cp) {
				this.cp = cp;
			}

			public String getDirection() {
				return direction;
			}

			public void setDirection(String direction) {
				this.direction = direction;
			}

			public String getDistance() {
				return distance;
			}

			public void setDistance(String distance) {
				this.distance = distance;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getPoiType() {
				return poiType;
			}

			public void setPoiType(String poiType) {
				this.poiType = poiType;
			}

			public PointBean getPoint() {
				return point;
			}

			public void setPoint(PointBean point) {
				this.point = point;
			}

			public String getTag() {
				return tag;
			}

			public void setTag(String tag) {
				this.tag = tag;
			}

			public String getTel() {
				return tel;
			}

			public void setTel(String tel) {
				this.tel = tel;
			}

			public String getUid() {
				return uid;
			}

			public void setUid(String uid) {
				this.uid = uid;
			}

			public String getZip() {
				return zip;
			}

			public void setZip(String zip) {
				this.zip = zip;
			}

			public ParentPoiBean getParent_poi() {
				return parent_poi;
			}

			public void setParent_poi(ParentPoiBean parent_poi) {
				this.parent_poi = parent_poi;
			}

			public static class PointBean {
				/**
				 * x : 113.43064467740132 y : 23.163091382805522
				 */

				private double x;
				private double y;

				public double getX() {
					return x;
				}

				public void setX(double x) {
					this.x = x;
				}

				public double getY() {
					return y;
				}

				public void setY(double y) {
					this.y = y;
				}
			}

			public static class ParentPoiBean {
				/**
				 * name : 广东省食品药品职业技术学校 tag : 教育培训;中学 addr : 广州市天河区大观街639号 point
				 * : {"x":113.43179450845372,"y":23.16309968930104} direction :
				 * 西北 distance : 403 uid : c9eb3a6f7b0c5135a3acc479
				 */

				private String name;
				private String tag;
				private String addr;
				private PointBeanX point;
				private String direction;
				private String distance;
				private String uid;

				public String getName() {
					return name;
				}

				public void setName(String name) {
					this.name = name;
				}

				public String getTag() {
					return tag;
				}

				public void setTag(String tag) {
					this.tag = tag;
				}

				public String getAddr() {
					return addr;
				}

				public void setAddr(String addr) {
					this.addr = addr;
				}

				public PointBeanX getPoint() {
					return point;
				}

				public void setPoint(PointBeanX point) {
					this.point = point;
				}

				public String getDirection() {
					return direction;
				}

				public void setDirection(String direction) {
					this.direction = direction;
				}

				public String getDistance() {
					return distance;
				}

				public void setDistance(String distance) {
					this.distance = distance;
				}

				public String getUid() {
					return uid;
				}

				public void setUid(String uid) {
					this.uid = uid;
				}

				public static class PointBeanX {
					/**
					 * x : 113.43179450845372 y : 23.16309968930104
					 */

					private double x;
					private double y;

					public double getX() {
						return x;
					}

					public void setX(double x) {
						this.x = x;
					}

					public double getY() {
						return y;
					}

					public void setY(double y) {
						this.y = y;
					}
				}
			}
		}
	}
}
