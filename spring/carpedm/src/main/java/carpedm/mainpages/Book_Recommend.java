package carpedm.mainpages;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.GZIPInputStream;


public class Book_Recommend {

	// yes24에서 isbn으로 검색하여 기존 책의 yes24 책 id값을 가져옴
	public String yes24SearchISBN(String isbn) {
		String result = "";
		try {
			long unixTime = System.currentTimeMillis();
			URL url_a = new URL("https://www.yes24.com/Product/Search?domain=ALL&query=" + isbn);
			HttpURLConnection connection_a = (HttpURLConnection) url_a.openConnection();

			// 헤더설정
//			configureConnection(connection_a, "" + unixTime, "GET");
			connection_a.setRequestMethod("GET");
			connection_a.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
			connection_a.setRequestProperty("Content-Type", "application/json");
//			int responseCode = connection_a.getResponseCode();
//			System.out.println("Response Code: " + responseCode);

			// 웹 페이지 내용 읽기
			StringBuffer rs_a = new StringBuffer();
			if ("gzip".equals(connection_a.getContentEncoding())) {
				GZIPInputStream gzipInputStream = new GZIPInputStream(connection_a.getInputStream());
				BufferedReader in_a = new BufferedReader(
						new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8));
				String inputLine;
				while ((inputLine = in_a.readLine()) != null) {
					rs_a.append(inputLine);
				}
				// in_a.close();//지역변수라 필요없어짐

			} else {
				BufferedReader in_a = new BufferedReader(
						new InputStreamReader(connection_a.getInputStream(), StandardCharsets.UTF_8));
				String inputLine;
				while ((inputLine = in_a.readLine()) != null) {
					rs_a.append(inputLine);
				}
				// in_a.close();//지역변수라 필요없어짐
			}

			connection_a.disconnect();
//			System.out.println(rs_a.toString());
			result = getDataOne(rs_a.toString(), "data-goods-no=\"", "\"");

			System.out.println(result);
			System.out.println("yes24 isbn검색 걸린시간 : " + (System.currentTimeMillis() - unixTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 연관책을 가져옴(셀레니움느려터지고 불안정해서 사용하게 됨)
	public ArrayList<Map<String, String>> Httpgetyes24(String url) {
		ArrayList<Map<String, String>> result_list = new ArrayList<Map<String, String>>();

		try {
			long unixTime = System.currentTimeMillis();

			URL url_a = new URL(url);
			HttpURLConnection connection_a = (HttpURLConnection) url_a.openConnection();
			System.out.println(url);

			connection_a.setRequestMethod("GET");
			connection_a.setRequestProperty("Accept", "application/json"); // 필요한 경우
			connection_a.setRequestProperty("host", "www.yes24.com");
			connection_a.setRequestProperty("Referer", "https://www.yes24.com/");
			connection_a.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
//			connection_a.setRequestProperty("Content-Type", "application/json");
			int responseCode = connection_a.getResponseCode();
			System.out.println("Response Code: " + responseCode);

			// 웹 페이지 내용 읽기
			StringBuffer rs_a = new StringBuffer();
			if ("gzip".equals(connection_a.getContentEncoding())) {
				GZIPInputStream gzipInputStream = new GZIPInputStream(connection_a.getInputStream());
				BufferedReader in_a = new BufferedReader(
						new InputStreamReader(gzipInputStream, StandardCharsets.UTF_8));
				String inputLine;
				while ((inputLine = in_a.readLine()) != null) {
					rs_a.append(inputLine);
				}

			} else {
				BufferedReader in_a = new BufferedReader(
						new InputStreamReader(connection_a.getInputStream(), StandardCharsets.UTF_8));
				String inputLine;
				while ((inputLine = in_a.readLine()) != null) {
					rs_a.append(inputLine);
				}
			}

			connection_a.disconnect();

			String book_list_text = getDataOne(rs_a.toString(), "id=\"nomiBoxRoolGrp_buyNCateGoodsWrap\"",
					"class=\"yPagenNum\"");
			String[] book_list = getDataList(book_list_text, "<li id=\"recommend_goods_area\"", "</li>");

			// 약 16개의 책을 가져오는데, 랜덤으로 섞어서 3개만 가져오도록 함
			shuffleArray(book_list);

			System.out.println("책 개수:" + book_list.length);
			for (int i = 0; i < book_list.length; i++) {
				// 책 이미지쪽을 통채로 오려냄(제목까지 포함되어있기 때문)
				String book = getDataOne(book_list[i], "<img class", "</a");
				// 책 이미지 url
				String b_img = getDataOne(book, "data-original=\"", "\"");
				// 책 제목
				String b_title = getDataOne(book, " alt=\"", "\"");
				// 책 저자
				String b_auth = getDataOne(book_list[i], "class=\"goods_auth\">", "</span");
				//책 url
				String b_src = getDataOne(book_list[i], "a href=\"", "\"");

				System.out.println(b_img);
				System.out.println(b_title);
				System.out.println(b_auth);
				System.out.println(b_src);
				
//				System.out.println(book_list[i]);
				Map<String, String> map = new HashMap<String, String>();
				map.put("b_img", b_img);
				map.put("b_title", b_title);
				map.put("b_auth", b_auth);
				map.put("b_src", "https://www.yes24.com"+b_src);
				result_list.add(map);
				if (i >= 2) {
					break;
				}
			}
//			result = getDataOne(rs_a.toString(), "data-goods-no=\"", "\"");

//			System.out.println(result);
			System.out.println("yes24 http 검색 걸린시간 : " + (System.currentTimeMillis() - unixTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_list;
	}

	// 단어 잘라내는 메소드
	public String getDataOne(String source, String start, String endString) {
		int startIndex = source.indexOf(start);
		int endIndex;

		if (endString == null || endString.isEmpty()) {
			endIndex = source.length();
		} else {
			endIndex = source.indexOf(endString, startIndex + start.length());
			if (endIndex == -1) { // endString이 소스 내에 없는 경우, 소스 문자열의 끝까지를 지정
				endIndex = source.length();
			}
		}

		if (startIndex != -1) {
			// startIndex + start.length()를 해줌으로써, 시작 태그 바로 뒤의 위치를 획득
			// endIndex는 종료 태그의 시작 위치 또는 문자열의 끝이므로, 이 사이의 문자열을 추출
			return source.substring(startIndex + start.length(), endIndex);
		} else {
			return "";
		}
	}

	// 여러 개의 문자열을 추출하여 배열로 반환하는 메소드
	public String[] getDataList(String source, String start, String endString) {
		List<String> resultList = new ArrayList();
		int startIndex = 0;
		int endIndex;

		while (true) {
			startIndex = source.indexOf(start, startIndex);
			if (startIndex == -1)
				break; // 더 이상 시작 문자열을 찾을 수 없으면 반복 종료

			endIndex = source.indexOf(endString, startIndex + start.length());
			if (endIndex == -1) {
				endIndex = source.length();
			}

			// 문자열 추출 및 리스트에 추가
			String extracted = source.substring(startIndex + start.length(), endIndex);
			resultList.add(extracted);

			// 다음 검색을 위해 startIndex 업데이트
			startIndex = endIndex + endString.length();
		}

		// 결과 리스트를 배열로 변환하여 반환
		return resultList.toArray(new String[0]);
	}

	// Fisher-Yates shuffle 알고리즘을 사용하여 배열을 섞는 메소드
	public static void shuffleArray(String[] book_list) {
		Random rnd = new Random();

		for (int i = book_list.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);

			// 현재 원소와 랜덤하게 선택된 원소를 교환
			String temp = book_list[index];
			book_list[index] = book_list[i];
			book_list[i] = temp;
		}
	}
}
