<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
	xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" layout:decorate="~{template}"
	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity5">

<head>
	<link th:href="@{/css/countdown.css}" rel="stylesheet" />
	<script type="text/javascript" th:src="@{/js/countdown.js}"></script>

	<!-- Font Awesome for Icons -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" />

	<style>
		.fa-plane {
			color: #007bff;
			font-size: 4rem;
			animation: fly 2s infinite ease-in-out;
		}

		@keyframes fly {
			0% {
				transform: translateY(0);
			}

			50% {
				transform: translateY(-10px);
			}

			100% {
				transform: translateY(0);
			}
		}
	</style>
</head>

<body>
	<div layout:fragment="content">
		<div class="container" style="align-items: center;">
			<i class="fa-solid fa-plane text-info"></i>
			<h1 id="headline" class="text-info">Chuyến bay sắp tới của tôi</h1>

			<section th:if="${route != null}">
				<div th:object="${route}">
					<div th:if="${#lists.size(route.flights) == 1}">
						<div th:each="flight : ${route.flights}" class="d-flex justify-content-center">
							<div th:if="${flightStat.index == 0}">
								<div class="flight-info-card" style="
    background-color: #f4f6f9;
    border: 2px solid #e0e0e0;
    border-radius: 10px;
    padding: 20px;
    margin-bottom: 15px;
    box-shadow: 0 4px 6px rgba(0,0,0,0.1);
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    gap: 20px;">

									<!-- Departure Date -->
									<div class="flight-detail" style="
        display: flex;
        align-items: center;
        font-size: 1.2rem;">

										<i class="fa-solid fa-calendar-day" style="
            color: #007bff;
            font-size: 1.5rem;
            margin-right: 10px;"></i>

										<span class="label" style="
            font-weight: 600;
            color: #555;
            margin-right: 8px;">Ngày khởi hành:</span>

										<span class="value" style="
            font-weight: 500;
            color: #333;" th:text="${flight.departureDate}"></span>
									</div>

									<!-- Departure Location -->
									<div class="flight-detail" style="
        display: flex;
        align-items: center;
        font-size: 1.2rem;">

										<i class="fa-solid fa-plane-departure" style="
            color: #007bff;
            font-size: 1.5rem;
            margin-right: 10px;"></i>

										<span class="label" style="
            font-weight: 600;
            color: #555;
            margin-right: 8px;">Từ:</span>

										<span class="value" style="
            font-weight: 500;
            color: #333;" th:text="${flight.departureAirport.city.name + ', ' + flight.departureAirport.city.country.name}"></span>
									</div>

									<!-- Arrival Location -->
									<div class="flight-detail" style="
        display: flex;
        align-items: center;
        font-size: 1.2rem;">

										<i class="fa-solid fa-plane-arrival" style="
            color: #007bff;
            font-size: 1.5rem;
            margin-right: 10px;"></i>

										<span class="label" style="
            font-weight: 600;
            color: #555;
            margin-right: 8px;">Đến:</span>

										<span class="value" style="
            font-weight: 500;
            color: #333;" th:text="${flight.arrivalAirport.city.name + ', ' + flight.arrivalAirport.city.country.name}"></span>
									</div>
								</div>

							</div>
						</div>
					</div>

					<div th:if="${#lists.size(route.flights) == 2}" class="d-flex justify-content-center">
						<div th:each="flight : ${route.flights}">
							<div th:if="${flightStat.index == 0}">
								<span class="me-4" th:text="${'Departure On ' + flight.departureDate}"></span>
								<span class="me-4"
									th:text="${'From ' + flight.departureAirport.city.name + ', ' + flight.departureAirport.city.country.name}"></span>
							</div>
							<div th:if="${flightStat.index == 1}">
								<span
									th:text="${'To ' + flight.arrivalAirport.city.name + ', ' + flight.arrivalAirport.city.country.name}"></span>
							</div>
						</div>
					</div>

					<input type="hidden" id="departureDate" th:value="${route.flights[0].departureDate}" />
				</div>

				<div id="countdown">
					<ul>
						<li class="countdownTitle"><span id="days"></span>ngày</li>
						<li class="countdownTitle"><span id="hours"></span>Giờ</li>
						<li class="countdownTitle"><span id="minutes"></span>Phút</li>
						<li class="countdownTitle"><span id="seconds"></span>Giây</li>
					</ul>
				</div>

				<script>
					$(document).ready(function () {
						startCountdown();
					});
				</script>
			</section>

			<section th:if="${route == null}">
				<div class="container text-center">
					<br />
					<span class="alert alert-primary w-40" role="alert">
						Không có tuyến bay nào, hãy cùng chúng tôi khám phá một cuộc phiêu lưu mới!
					</span>
				</div>
			</section>
		</div>
	</div>
</body>

</html>