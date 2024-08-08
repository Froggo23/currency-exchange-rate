package com.choe.currencyapp;

import com.choe.currencyapp.entity.history.HolidayEntity;
import com.choe.currencyapp.entity.history.Item;
import com.choe.currencyapp.repository.HolidayApiRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
class CurrencyAppApplicationTests {

	@Autowired
	HolidayApiRepository holidayApiRepository;

	@Test
	void contextLoads() {
		// Retrieve holiday data for October 2024
		HolidayEntity holidayEntity = holidayApiRepository.getHolidayData("2024-10-03");

		// Dates to check
		String date1 = "20241003";
		String date2 = "20241004";

		List<Item> itemList = holidayEntity.getBody().getItems().getItemList();
		System.out.println(itemList);
		Set<String> set = new HashSet<>();

		for(int i = 0; i < itemList.size(); i++){
			Item item = itemList.get(i);
			String locDate = item.getLocdate();
			set.add(locDate);
		}

//		System.out.println(set.contains(date1));
//		System.out.println(set.contains(date2));

		Assertions.assertTrue(isHoliday(holidayEntity, date1));
		Assertions.assertFalse(isHoliday(holidayEntity, date2));



//		// Assert the results (assuming JUnit assertions)
//		assertTrue(isDate1Holiday, date1 + " should be a holiday.");
//		assertFalse(isDate2Holiday, date2 + " should not be a holiday.");
	}

	private boolean isHoliday(HolidayEntity holidayEntity, String date) {
		if (holidayEntity == null || holidayEntity.getBody() == null || holidayEntity.getBody().getItems() == null) {
			return false;
		}
		return holidayEntity.getBody().getItems().getItemList().stream()
				.anyMatch(item -> date.equals(item.getLocdate()));
	}
}
