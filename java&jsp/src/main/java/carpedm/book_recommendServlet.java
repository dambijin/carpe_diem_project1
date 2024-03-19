package carpedm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/book_recommend")
public class book_recommendServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String isbn = request.getParameter("isbn");
		if (isbn == null || "".equals(isbn)) {
			isbn = "";
		}
		book_AIRecommend(isbn);
	}

	// ISBN값을 기준으로 값을 가져오는 웹크롤링 함수
	private Map<String, String> book_AIRecommend(String isbn) {
		// 9788901271729 콜드리딩
		// 9791168473690 세이노
		Map<String, String> result = new HashMap<String, String>();
		System.out.println(wrtn_qna("isbn이 9791198530349인 책과 비슷한 책들 5개정도 추천해줘"));
		return result;
	}

	// 뤼튼에게 요청하고 받아내기(gpt4)
	String wrtn_qna(String query) {
		// 9788901271729 콜드리딩
		// 9791168473690 세이노
		String result = "";
		try {
			long unixTime = System.currentTimeMillis();
			URL url_q = new URL("https://william.wow.wrtn.ai/chat/anonymous/start?platform=web&mode=chat&model=gpt4");
			HttpURLConnection connection_q = (HttpURLConnection) url_q.openConnection();

			// HTTP POST 요청 설정
			connection_q.setRequestMethod("POST");

			// POST 요청에 데이터를 포함시키기 위해 출력 스트림 활성화
			connection_q.setDoOutput(true);

			// 요청 헤더 설정 (예: 콘텐츠 타입을 설정하는 경우)
			connection_q.setRequestProperty("Content-Type", "application/json");
			connection_q.setRequestProperty("Referer", "https://wrtn.ai/");
			connection_q.setRequestProperty("x-wrtn-id",
					"W1.2.2501006464537361220005373612200051024128024.obV9sF_ZlFvDyD7GEfHR4." + unixTime);
//			connection.setRequestProperty("x-wrtn-id", "W1.2.2501006464537361220005373612200051024128024.obV9sF_ZlFvDyD7GEfHR4.1710747589240");
//			connection.setRequestProperty("x-wrtn-id", "W1.2.2501006464537361220005373612200051024128024.YXvD2j0myi8uUHCZ60lYR.1710806620964");

			// 전송할 데이터 준비
			String jsonInputString_q = "{\"message\": \"" + query + "\"}";

			// 요청 본문에 데이터 쓰기
			try (OutputStream os = connection_q.getOutputStream()) {
				byte[] input = jsonInputString_q.getBytes("utf-8");
				os.write(input, 0, input.length);
			}
//			// 응답 코드 확인
//			int responseCode = connection.getResponseCode();
//			System.out.println("Response Code: " + responseCode);

			// 웹 페이지 내용 읽기
			BufferedReader in = new BufferedReader(new InputStreamReader(connection_q.getInputStream()));
			String inputLine;
			StringBuffer rs_q = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				rs_q.append(inputLine);
			}
			in.close();

			// 응답 내용 출력
			System.out.println(rs_q.toString());
			String assi_data = getDataOne(rs_q.toString(), "data\":\"", "\"");
			System.out.println(assi_data);
			System.out.println("걸린시간 : " + (System.currentTimeMillis() - unixTime));
			// data값이 있다면 답을 가져오기(1단계, 일종의 답변요청)
			if (!"".equals(assi_data) && assi_data != null) {
				// 뤼튼에게 답 받아내기(한번 요청을 하고 나서야 아래 요청이 가능해진다)
				URL url_a = new URL("https://william.wow.wrtn.ai/chat/anonymous/" + assi_data
						+ "?model=gpt4.0&platform=web&user=nobody");
//				URL url_a = new URL("https://william.wow.wrtn.ai/chat/anonymous/"+assi_data+"/result");
				HttpURLConnection connection_a = (HttpURLConnection) url_a.openConnection();

				// HTTP GET 요청 설정
				connection_a.setRequestMethod("GET");

				// 요청 헤더 설정 (예: 콘텐츠 타입을 설정하는 경우)
				connection_a.setRequestProperty("Content-Type", "application/json");
				connection_a.setRequestProperty("Referer", "https://wrtn.ai/");
				connection_a.setRequestProperty("x-wrtn-id",
						"W1.2.2501006464537361220005373612200051024128024.obV9sF_ZlFvDyD7GEfHR4." + unixTime);

				// 웹 페이지 내용 읽기
				BufferedReader in_a = new BufferedReader(new InputStreamReader(connection_a.getInputStream()));

				// 응답 코드 확인
				int responseCode = connection_a.getResponseCode();
				System.out.println("Response Code: " + responseCode);
				System.out.println("단순히 받아오기만 함 : " + (System.currentTimeMillis() - unixTime));
//				String inputLine_a;
//				StringBuffer rs_a = new StringBuffer();
//				while ((inputLine_a = in_a.readLine()) != null) {
//					rs_a.append(inputLine_a);
//				}
//				result = rs_a.toString();
//				System.out.println(result);
				in_a.close();

				System.out.println("걸린시간 : " + (System.currentTimeMillis() - unixTime));
			}
			//최종적으로 정리된 내용을 받아오기위한 get
			if (!"".equals(assi_data) && assi_data != null) {
				// 뤼튼에게 답 받아내기
//				URL url_a = new URL("https://william.wow.wrtn.ai/chat/anonymous/"+assi_data+"?model=gpt4.0&platform=web&user=nobody");
				URL url_a = new URL("https://william.wow.wrtn.ai/chat/anonymous/" + assi_data + "/result");
				HttpURLConnection connection_a = (HttpURLConnection) url_a.openConnection();

				// HTTP GET 요청 설정
				connection_a.setRequestMethod("GET");

				// 요청 헤더 설정 (예: 콘텐츠 타입을 설정하는 경우)
				connection_a.setRequestProperty("Content-Type", "application/json");
				connection_a.setRequestProperty("Referer", "https://wrtn.ai/");
				connection_a.setRequestProperty("x-wrtn-id",
						"W1.2.2501006464537361220005373612200051024128024.obV9sF_ZlFvDyD7GEfHR4." + unixTime);

				// 웹 페이지 내용 읽기
				BufferedReader in_a = new BufferedReader(new InputStreamReader(connection_a.getInputStream()));
				String inputLine_a;
				StringBuffer rs_a = new StringBuffer();

				while ((inputLine_a = in_a.readLine()) != null) {
					rs_a.append(inputLine_a);
				}
				in_a.close();
				result = rs_a.toString();
				System.out.println(result);
				System.out.println("걸린시간 : " + (System.currentTimeMillis() - unixTime));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 단어 잘라내는 함수
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
}
