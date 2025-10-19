<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
	xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" layout:decorate="~{template}"
	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity5">

<head>

	<script type="text/javascript" th:src="@{/js/add.js}"></script>
	<script type="text/javascript" th:src="@{/js/airports.js}"></script>

	<script type="text/javascript">

		$('.selectpicker').selectpicker();

		$(document).ready(function () {
			fetchArrivalAirports();

			const initialDuration = $('#durationInput').val();
			$('#durationInput').val(formatDuration(initialDuration));


			$('#durationInput').on('blur', function () {
				const formattedDuration = formatDuration($(this).val());
				$(this).val(formattedDuration);
			});
		});


		function formatDuration(duration) {
			const match = duration.match(/PT(\d+)H/);
			if (match && match[1]) {
				const hours = parseInt(match[1]);
				const formattedHours = ('0' + hours).slice(-2);
				return formattedHours + ':00';
			}
			return duration;
		}

	</script>

</head>

<body>
	<div layout:fragment="content">
		<div class="container">
			<h2 class="fw-bold mb-4 text-center">Thêm chuyến bay</h2>
			<p class="text-muted">
				Vui lòng điền vào mẫu dưới đây để thêm chuyến bay thẳng mới vào hệ thống.
			</p>
		</div>

		<div class="container">
			<form id="addForm" th:action="@{/adminAddFlight/ProcessFlightInsertion}" th:object="${flight}"
				method="post">
				<div class="row">
					<div class="col-3">
						<div class="form-floating mb-3">
							<select name="planeID" th:field="*{plane.id}" class="selectpicker form-control"
								data-size="5">
								<option value="0">Lựa chọn</option>
								<option th:each="plane : ${planesList}" th:value="${plane.id}"
									th:text="${plane.description}" th:data-subtext="${'available: ' + plane.availableQuantity +
                        									', capacity: ' + (plane.numOfRows * plane.numOfSeatsPerRow)}"
									th:selected="${#strings.equals(#strings.toString(plane.id), #strings.toString(flight.plane.id))}"
									th:disabled="${#strings.equals(#strings.toString(plane.availableQuantity), #strings.toString(0))}">
								</option>
							</select> <label for="planeID">Máy bay*</label>
						</div>
						<div class="text-danger">
							<p th:if="${#fields.hasErrors('plane.id')}" th:errors="*{plane.id}" class="error-message">
							</p>
						</div>
					</div>
					<div class="col-4">
						<div class="form-floating mb-3">
							<select name="departureAirportID" id="departureAirportID" th:field="*{departureAirport.id}"
								class="selectpicker form-control" data-size="5">
								<option value="0">Lựa chọn</option>
								<option th:each="option : ${departureAirportsList}" th:value="${option.id}"
									th:text="${option.code}"
									th:data-subtext="${option.city.name + ', ' + option.city.country.name}"></option>
							</select> <label for="departureAirportID">Sân bay khởi hành*</label>
						</div>
						<div class="text-danger">
							<p th:if="${#fields.hasErrors('departureAirport.id')}" th:errors="*{departureAirport.id}"
								class="error-message"></p>
						</div>
					</div>
					<div class="col-4">
						<div class="form-floating mb-3">
							<select name="arrivalAirportID" id="arrivalAirportID" th:field="*{arrivalAirport.id}"
								class="selectpicker form-control" data-size="5">
								<option value="0">Lựa chọn</option>
							</select> <label for="arrivalAirportID">Sân bay đến*</label>
						</div>
						<div class="text-danger">
							<p th:if="${#fields.hasErrors('arrivalAirport.id')}" th:errors="*{arrivalAirport.id}"
								class="error-message"></p>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-2">
						<div class="form-floating mb-3">
							<input type="text" name="departureDate" th:field="*{departureDate}" id="departureDate"
								class="form-control" placeholder="Departure Date"> <label for="departureDate">Ngày khởi
								hành*</label>
							<script>
								flatpickr("#departureDate",
									{
										dateFormat: "Y-m-d",
										minDate: "today",
										altInput: true,
										altFormat: "d.m.Y",
									});
							</script>
						</div>
						<div class="text-danger">
							<p th:if="${#fields.hasErrors('departureDate')}" th:errors="*{departureDate}"
								class="error-message"></p>
						</div>
					</div>
					<div class="col-2">
						<div class="form-floating mb-3">
							<div class="form-floating mb-3">
								<input type="text" name="departureTime" th:field="*{departureTime}" id="departureTime"
									class="form-control" placeholder="Departure Time"> <label for="departureTime">Giờ
									khởi hành*</label>
								<script>
									flatpickr("#departureTime",
										{
											enableTime: true,
											noCalendar: true,
											dateFormat: "H:i:ss",
										});
								</script>
							</div>
						</div>
						<div class="text-danger">
							<p th:if="${#fields.hasErrors('departureTime')}" th:errors="*{departureTime}"
								class="error-message"></p>
						</div>
					</div>
					<div class="col-2">
						<div class="form-floating mb-3">
							<input type="text" name="duration" id="durationInput" th:field="*{duration}"
								class="form-control" placeholder="Duration"> <label for="duration">Ví dụ 00:50 </small>
						</div>
						<div class="text-danger">
							<p th:if="${#fields.hasErrors('duration')}" th:errors="*{duration}" class="error-message">
							</p>
						</div>
					</div>
					<div class="col-2">
						<div class="form-floating mb-3">
							<input type="text" name="ticketPrice" th:field="*{ticketPrice}" class="form-control"
								placeholder="Ticket Price"> <label for="ticketPrice">Giá vé* (VND)</label>
						</div>
						<div class="text-danger">
							<p th:if="${#fields.hasErrors('ticketPrice')}" th:errors="*{ticketPrice}"
								class="error-message"></p>
						</div>
					</div>
				</div>

				<button type="submit" class="btn btn-primary rounded-0" style="display: block; margin: auto;">
					<i class="fa-solid fa-plus me-2"></i>Thêm
				</button>
			</form>
		</div>
	</div>
</body>

</html>