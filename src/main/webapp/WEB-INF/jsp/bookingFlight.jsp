<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
	xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" layout:decorate="~{template}"
	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity5">

<head>

	<!-- css files -->
	<link th:href="@{/css/stepper.css}" rel="stylesheet" />
	<link th:href="@{/css/timeline.css}" rel="stylesheet" />

</head>

<body>
	<div layout:fragment="content">

		<!-- stepper -->
		<div class="container padding-bottom-3x mb-1">
			<div class="card mb-1 border-0">
				<h2 class="fw-bold mb-1 text-center">Đặt vé</h2>
				<div class="card-body">
					<div
						class="steps d-flex flex-wrap flex-sm-nowrap justify-content-between padding-top-2x padding-bottom-1x">
						<div class="step completed">
							<div class="step-icon-wrap">
								<div class="step-icon">
									<i class="fa-solid fa-earth-americas"></i>
								</div>
							</div>
							<h4 class="step-title">Chuyến đi</h4>
						</div>
						<div class="step">
							<div class="step-icon-wrap">
								<div class="step-icon">
									<i class="fa-solid fa-plane"></i>
								</div>
							</div>
							<h4 class="step-title">Chuyến bay</h4>
						</div>
						<div class="step">
							<div class="step-icon-wrap">
								<div class="step-icon">
									<i class="fa-solid fa-users"></i>
								</div>
							</div>
							<h4 class="step-title">Hành khách</h4>
						</div>
						<div class="step">
							<div class="step-icon-wrap">
								<div class="step-icon">
									<i class="fa-solid fa-chair"></i>
								</div>
							</div>
							<h4 class="step-title">Chỗ ngồi</h4>
						</div>
						<div class="step">
							<div class="step-icon-wrap">
								<div class="step-icon">
									<i class="fa-solid fa-suitcase-rolling"></i>
								</div>
							</div>
							<h4 class="step-title">Hành lý</h4>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Form -->
		<section th:if="${!#lists.isEmpty(routesList)}">
			<div th:each="route : ${routesList}">
				<div class="container">
					<div class="card mb-3">
						<div class="card-header">
							<div class="row">
								<div class="col">
									<p th:text="${'Tuyến #' + route.routeNumber}"></p>
								</div>
								<div class="col">
									<p
										th:text="${'Giá vé cho hành khách lẻ: ' + '$' + #numbers.formatDecimal(route.routePriceForSinglePassenger, 0, 'COMMA', 0, 'POINT')}">
									</p>
								</div>
								<div class="col d-flex justify-content-end">
									<form th:action="@{/bookingFlight/ProcessBookingFlight}" method="post">
										<input type="hidden" name="routeNumber" th:value="${route.routeNumber}" />
										<button type="submit" class="btn btn-primary rounded-0" style="
        background-color: #007bff; 
        color: #ffffff; 
        border: none; 
        padding: 12px 30px; 
        font-size: 1.1rem; 
        font-weight: bold; 
        border-radius: 8px; 
        display: inline-flex; 
        align-items: center; 
        justify-content: center; 
        transition: all 0.3s ease-in-out; 
        box-shadow: 0 5px 15px rgba(0, 123, 255, 0.3); 
        position: relative; 
        overflow: hidden;" onmouseover="this.style.backgroundColor='#0056b3'; 
                 this.style.boxShadow='0 10px 20px rgba(0, 123, 255, 0.4)'; 
                 this.style.transform='translateY(-3px)'; 
                 this.querySelector('i').style.transform='translateX(5px)';" onmouseout="this.style.backgroundColor='#007bff'; 
                this.style.boxShadow='0 5px 15px rgba(0, 123, 255, 0.3)'; 
                this.style.transform='translateY(0)'; 
                this.querySelector('i').style.transform='translateX(0)';" onmousedown="this.style.transform='scale(0.97)'; 
                this.style.boxShadow='0 5px 10px rgba(0, 123, 255, 0.2)';"
											onmouseup="this.style.transform='translateY(0)';">
											<i class="fa-regular fa-circle-check me-2 p-2" style="
            font-size: 1.2rem; 
            transition: all 0.3s ease-in-out; 
            margin-right: 10px;">
											</i>Điền thông tin
										</button>

									</form>
								</div>
							</div>
						</div>
						<br /> <br />
						<div class="card-body">
							<div class="row">
								<div class="col-lg-11">
									<div class="horizontal-timeline">
										<ul class="list-inline items">
											<li th:each="flight : ${route.flights}" th:if="${flightStat.index == 0}"
												class="list-inline-item items-list">
												<div class="px-3">
													<div class="event-date badge bg-info">
														<i class="fa-regular fa-calendar"></i> <span
															th:text="${flight.departureDate + ' ' + flight.departureTime}"></span>
													</div>
													<i class="fa-solid fa-plane-departure text-info"></i>
													<h5 class="pt-2"
														th:text="${flight.departureAirport.city.name + ', ' + flight.departureAirport.city.country.name}">
													</h5>
													<p class="text-muted" th:text="${flight.departureAirport.code}"></p>
												</div>
											</li>

											<li th:each="flight : ${route.flights}" th:if="${flightStat.index == 0}"
												class="list-inline-item items-list">
												<div class="px-3 text-muted">
													<small
														th:text="${T(com.example.SwipeFlight.server.utils.Formatter).formatDuration(flight.duration) + ' giờ'}"></small>
													<br /> <small th:text="${'Flight ID: ' + flight.id}"></small>
													<div class="pt-2">
														<i class="fa-solid fa-circle-info"></i>
													</div>
												</div>
											</li>
											<li th:each="flight : ${route.flights}" th:if="${flightStat.index == 0}"
												class="list-inline-item items-list">
												<div class="px-3">
													<div class="event-date badge bg-info">
														<i class="fa-regular fa-calendar"></i> <span
															th:text="${flight.arrivalDate + ' ' + flight.arrivalTime}"></span>
													</div>
													<i class="fa-solid fa-plane-arrival text-info"></i>
													<h5 class="pt-2"
														th:text="${flight.arrivalAirport.city.name + ', ' + flight.arrivalAirport.city.country.name}">
													</h5>
													<p class="text-muted" th:text="${flight.arrivalAirport.code}"></p>
												</div>
											</li>
											<li th:each="flight : ${route.flights}" th:if="${flightStat.index == 1}"
												class="list-inline-item items-list">
												<div class="px-3">
													<div class="event-date badge bg-info">
														<i class="fa-regular fa-calendar"></i> <span
															th:text="${flight.departureDate + ' ' + flight.departureTime}"></span>
													</div>
													<i class="fa-solid fa-plane-departure text-info"></i>
													<h5 class="pt-2"
														th:text="${flight.departureAirport.city.name + ', ' + flight.departureAirport.city.country.name}">
													</h5>
													<p class="text-muted" th:text="${flight.departureAirport.code}"></p>
												</div>
											</li>
											<!-- flight duration 2 -->
											<li th:each="flight : ${route.flights}" th:if="${flightStat.index == 1}"
												class="list-inline-item items-list">
												<div class="px-3 text-muted">
													<small
														th:text="${T(com.example.SwipeFlight.server.utils.Formatter).formatDuration(flight.duration) + ' hours'}"></small>
													<br /> <small th:text="${'Flight ID: ' + flight.id}"></small>
													<div class="pt-2">
														<i class="fa-solid fa-circle-info"></i>
													</div>
												</div>
											</li>
											<li th:each="flight : ${route.flights}" th:if="${flightStat.index == 1}"
												class="list-inline-item items-list">
												<div class="px-3">
													<div class="event-date badge bg-info">
														<i class="fa-regular fa-calendar"></i> <span
															th:text="${flight.arrivalDate + ' ' + flight.arrivalTime}"></span>
													</div>
													<i class="fa-solid fa-plane-arrival text-info"></i>
													<h5 class="pt-2"
														th:text="${flight.arrivalAirport.city.name + ', ' + flight.arrivalAirport.city.country.name}">
													</h5>
													<p class="text-muted" th:text="${flight.arrivalAirport.code}"></p>
												</div>
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>

		<section th:if="${#lists.isEmpty(routesList)}">
			<div class="container">
				<p class="text-danger">Không có chuyến bay nào tồn tại, vui lòng chọn ngày và/hoặc địa điểm khác.</p>
			</div>
		</section>

		<div class="d-flex justify-items-center mt-4 mb-2">
			<div class="container">
				<a class="btn btn-secondary rounded-0 me-4" href="javascript:history.back()"><i
						class="fa-solid fa-circle-arrow-left p-2"></i>Quay lại</a>
			</div>
		</div>

	</div>
</body>

</html>