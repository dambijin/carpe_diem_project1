package carpedm.mainpages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Book_Recommend {
	public ArrayList<Map<String, String>> selenium_getyes24() {
		ArrayList<Map<String, String>> result_list = new ArrayList<Map<String, String>>();

		long unixTime = System.currentTimeMillis();
		// WebDriverManager를 통해 크롬 드라이버를 자동으로 설정
		WebDriverManager.chromedriver().setup();
		WebDriverManager.chromedriver().timeout(3000).setup();
		// 헤드리스 모드 옵션 설정
		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--headless");
		// 크롬 드라이버를 사용하여 WebDriver 인스턴스 생성
		WebDriver driver = new ChromeDriver(options);
		System.out.println("크롬드라이버 세팅 걸린시간 : " + (System.currentTimeMillis() - unixTime));
		try {
			unixTime = System.currentTimeMillis();
			driver.get("https://www.yes24.com/Product/Goods/119782591");
			System.out.println("페이지이동 걸린시간 : " + (System.currentTimeMillis() - unixTime));

//			Thread.sleep(10000);
			String pageText = driver.getPageSource();
			String book_list_text = getDataOne(pageText, "id=\"nomiBoxRoolGrp_buyNCateGoodsWrap\"",
					"class=\"yPagenNum\"");
			String[] book_list = getDataList(book_list_text, "<li id=\"recommend_goods_area\"", "</li>");

			for (int i = 0; i < book_list.length; i++) {
				// 책 이미지쪽을 통채로 오려냄(제목까지 포함되어있기 때문)
				String book = getDataOne(book_list[i], "<img class", "</a");
				// 책 이미지 url
				String b_img = getDataOne(book, "data-original=\"", "\"");
				// 책 제목
				String b_title = getDataOne(book, " alt=\"", "\"");

				System.out.println(b_img);
				System.out.println(b_title);
				Map<String, String> map = new HashMap<String, String>();
				map.put("b_img", b_img);
				map.put("b_title", b_title);
				result_list.add(map);
				if (i >= 2) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 사용이 끝났으면, 드라이버를 종료합니다.
			driver.quit();
		}

		return result_list;
	}

	// 알라딘에서
	public void getBookRecommend(String isbn) {
		try {
			long unixTime = System.currentTimeMillis();
			// 뤼튼에게 답 받아내기
			URL url_a = new URL("https://www.yes24.com/Product/Goods/119782591");
			HttpURLConnection connection_a = (HttpURLConnection) url_a.openConnection();

			// 헤더설정
//			configureConnection(connection_a, "" + unixTime, "GET");
			connection_a.setRequestMethod("GET");
			connection_a.setRequestProperty("Content-Type", "application/json");
			int responseCode = connection_a.getResponseCode();
			System.out.println("Response Code: " + responseCode);
			// 웹 페이지 내용 읽기
			BufferedReader in_a = new BufferedReader(new InputStreamReader(connection_a.getInputStream()));
			StringBuffer rs_a = new StringBuffer();
			String inputLine;
			while ((inputLine = in_a.readLine()) != null) {
				rs_a.append(inputLine);
			}
			in_a.close();
			connection_a.disconnect();
			System.out.println(rs_a.toString());
			String result = getDataOne(rs_a.toString(), ",\"content\":\"", "\",\"status\"");

			System.out.println(result);
			System.out.println("GET2 걸린시간 : " + (System.currentTimeMillis() - unixTime));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ISBN값을 기준으로 값을 가져오는 웹크롤링 함수
	public Map<String, String> book_AIRecommend(String isbn) {
		Map<String, String> result = new HashMap<String, String>();
		// 9788901271729 콜드리딩
		// 9791168473690 세이노
		System.out.println(
				wrtn_qna("isbn값이 9788901271729인 책과 비슷한 책 5개 알려줘. 꼭 isbn값을 포함해야 해. 양식은 책 이름 : \\nisbn : 이렇게 작성해줘"));
		return result;
	}

	// 뤼튼에게 요청하고 받아내기(gpt4.0,wrtn_search)
	public String wrtn_qna(String query) {
		String result = "";
		try {
			String assi_data = "";
			String ai_model = "gpt4";
			long unixTime = System.currentTimeMillis();
			URL url_q = new URL(
					"https://william.wow.wrtn.ai/chat/anonymous/start?platform=web&mode=chat&model=" + ai_model);
			HttpURLConnection connection_q = (HttpURLConnection) url_q.openConnection();

			// 요청 헤더 설정
			configureConnection(connection_q, "" + unixTime, "POST");
			// POST 요청에 데이터를 포함시키기 위해 출력 스트림 활성화
			connection_q.setDoOutput(true);
			// 전송할 쿼리 준비
			String jsonInputString_q = "{\"message\": \"" + query + "\"}";
			// 요청에 데이터 쓰기
			try (OutputStream os = connection_q.getOutputStream()) {
				byte[] input = jsonInputString_q.getBytes("utf-8");
				os.write(input, 0, input.length);
			}
//			// 응답 코드 확인
			int responseCode = connection_q.getResponseCode();
			System.out.println("Response Code: " + responseCode);

			// 웹 페이지 내용 읽기(여기선 getInputStream때 connect가 된다)
			BufferedReader in_q = new BufferedReader(new InputStreamReader(connection_q.getInputStream()));
			StringBuffer rs_q = new StringBuffer();
			String inputLine;

			while ((inputLine = in_q.readLine()) != null) {
				rs_q.append(inputLine);
			}
			in_q.close();
			connection_q.disconnect();
			// 응답 내용 출력
			System.out.println(rs_q.toString());
			assi_data = getDataOne(rs_q.toString(), "data\":\"", "\"");
			System.out.println(assi_data);
			System.out.println("POST 걸린시간 : " + (System.currentTimeMillis() - unixTime));

			// data값이 있다면 답을 가져오기(1단계, 일종의 답변요청)
			if (!"".equals(assi_data) && assi_data != null) {
				// 뤼튼에게 답 받아내기(한번 요청을 하고 나서야 아래 요청이 가능해진다)
				URL url_a = new URL("https://william.wow.wrtn.ai/chat/anonymous/" + assi_data + "?model=" + ai_model
						+ "&platform=web&user=nobody");
				HttpURLConnection connection_a = (HttpURLConnection) url_a.openConnection();
				// 헤더설정
				configureConnection(connection_a, "" + unixTime, "GET");
				// 응답 코드 확인(이걸 하면서 connect도 작동된다.)
				responseCode = connection_a.getResponseCode();
				System.out.println("Response Code: " + responseCode);
				System.out.println("GET1 단순한 응답완료 : " + (System.currentTimeMillis() - unixTime));

//				첫 번째 요청에서는 응답 본문을 사용하지 않으므로, 연결을 빠르게 종료
				connection_a.disconnect();
				System.out.println("GET1 걸린시간 : " + (System.currentTimeMillis() - unixTime));
			}
			// 최종적으로 정리된 내용을 받아오기위한 get
			if (!"".equals(assi_data) && assi_data != null) {
				for (int i = 0; i < 10; i++) {
					// 뤼튼에게 답 받아내기
					URL url_a = new URL("https://william.wow.wrtn.ai/chat/anonymous/" + assi_data + "/result");
					HttpURLConnection connection_a = (HttpURLConnection) url_a.openConnection();

					// 헤더설정
					configureConnection(connection_a, "" + unixTime, "GET");

					responseCode = connection_a.getResponseCode();
					System.out.println("Response Code: " + responseCode);
					if (responseCode == 500) {
						System.out.println("아직 응답을 받지 못해 10초 대기합니다.");
						Thread.sleep(10000); // 10초 대기
						continue;
					}
					// 웹 페이지 내용 읽기
					BufferedReader in_a = new BufferedReader(new InputStreamReader(connection_a.getInputStream()));
					StringBuffer rs_a = new StringBuffer();

					while ((inputLine = in_a.readLine()) != null) {
						rs_a.append(inputLine);
					}
					in_a.close();
					connection_a.disconnect();
					System.out.println(rs_a.toString());
					result = getDataOne(rs_a.toString(), ",\"content\":\"", "\",\"status\"");

					System.out.println(result);
					System.out.println("GET2 걸린시간 : " + (System.currentTimeMillis() - unixTime));
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 헤더 설정을 위한 메소드
	private void configureConnection(HttpURLConnection connection, String unixTime, String method)
			throws ProtocolException {
		connection.setRequestMethod(method);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
		connection.setRequestProperty("host", "william.wow.wrtn.ai");
		connection.setRequestProperty("Referer", "https://wrtn.ai/");
		connection.setRequestProperty("wrtn-locale", "ko-KR");
		connection.setRequestProperty("x-wrtn-id",
				"W1.2.2501006464537361220005373612200051024128024.zD5VRxF2iPK1GWakz6wvd." + unixTime);
//		"W1.2.2501006464537361220005373612200051024128024.obV9sF_ZlFvDyD7GEfHR4." + unixTime);
//		connection.setRequestProperty("x-wrtn-id", "W1.2.2501006464537361220005373612200051024128024.obV9sF_ZlFvDyD7GEfHR4.1710747589240");
//		connection.setRequestProperty("x-wrtn-id", "W1.2.2501006464537361220005373612200051024128024.YXvD2j0myi8uUHCZ60lYR.1710806620964");

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
			// startIndex + start.length()를 해줌으로써, 시작 태그 바로 뒤의 위치를 얻습니다.
			// endIndex는 종료 태그의 시작 위치 또는 문자열의 끝이므로, 이 사이의 문자열을 추출합니다.
			return source.substring(startIndex + start.length(), endIndex);
		} else {
			return "";
		}
	}

	// 여러 개의 문자열을 추출하여 배열로 반환하는 메소드
	public String[] getDataList(String source, String start, String endString) {
		List<String> resultList = new ArrayList<>();
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
}
