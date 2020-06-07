package com.zsq.SpringBootDemo.modules.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.zsq.SpringBootDemo.modules.commom.vo.Result;
import com.zsq.SpringBootDemo.modules.commom.vo.SearchVo;
import com.zsq.SpringBootDemo.modules.test.entity.City;
import com.zsq.SpringBootDemo.modules.test.service.CityService;

@RestController // 代表类里全是接口 ，相当于@Controller + @ResponseBody ,方法不用在@ResponseBody
@RequestMapping("/api")
public class CityController {

	@Autowired
	private CityService cityService;

	/**
	 * 127.0.0.1/api/cities/522 ------RequestMapping是get请求，通过mapper方式解决掉
	 * 
	 * @PathVariable 用来获取path路径中的参数信息
	 * 
	 * @param countryId
	 * @return
	 */
	@RequestMapping("/cities/{countryId}")
	public List<City> getCitiesByCountryId(@PathVariable int countryId) {
		return cityService.getCitiesByCountryId(countryId);
	}

	/**
	 * 127.0.0.1/api/city?cityName=Shanghai&localCityName=1111
	 * 
	 * @RequestParam 获取url里的参数信息，也就是上述显示这种get方式提交的信息
	 * 
	 * @param cityName
	 * @param localCityName
	 * @return
	 */
	@RequestMapping("/city")
	public City getCityByName(@RequestParam String cityName, @RequestParam String localCityName) {
		return cityService.getCityByName(cityName, localCityName);
	}

	
	/**
	 * 利用分页插件分页查询
	 * 127.0.0.1/api/cities?currentPage=1&pageSize=5&countryId=522
	 * @param currentPage
	 * @param pageSize
	 * @param countryId
	 * @return
	 */
	@RequestMapping("/cities")
	public PageInfo<City> getCitiesByPage(@RequestParam int currentPage,@RequestParam int pageSize,@RequestParam int countryId){
		return cityService.getCitiesByPage(currentPage, pageSize, countryId);
	}
	
	/**
	 * 多条数据查询，通常为了安全，使用post方式
	 * consumes = "application/json" 设置进入控制器的数据类型
	 * 使用@RequestBody来获取json字符串 并包装到修饰对象中
	 * 
	 * 127.0.0.1/api/cities ----post请求，直接浏览器访问不能满足测试条件，使用postman来测试
	 * 
	 * @param searchVo
	 * @return
	 */
	//@RequestMapping(value = "/cities",method = RequestMethod.POST,consumes = "application/json")
	@PostMapping(value = "/cities",consumes = "application/json")
	public PageInfo<City> getCitiesBySearchVo(@RequestBody SearchVo searchVo){
		return cityService.getCitiesBySearchVo(searchVo);
	}
	
	/**
	 * 添加数据---127.0.0.1/api/city ----一般使用post
	 * @param city
	 * @return
	 */
	@PostMapping(value = "/city",consumes = "application/json")
	public Result<City> insertCity(@RequestBody City city){
		return cityService.insertCity(city);
	}
	
	/**
	 * 更新数据---127.0.0.1/api/city一般使用put
	 * consumes = "application/x-www-form-urlencoded" 提交为form表单-----使用@ModelAttribute接收
	 * @param city
	 * @return
	 */
	@PutMapping(value = "/city",consumes = "application/x-www-form-urlencoded")  //请求方式不同，接口名可以一样
	public Result<City> updateCity(@ModelAttribute City city){
		return cityService.updateCity(city);
	}
	
	/**
	 * 删除数据---127.0.0.1/api/city--------@DeleteMapping
	 * @param cityId
	 * @return
	 */
	@DeleteMapping("/city/{cityId}")
	public Result<Object> deleteCity(@PathVariable int cityId){
		return cityService.deleteCity(cityId);
	}
	
}
