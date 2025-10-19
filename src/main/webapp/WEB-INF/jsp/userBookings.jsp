<!-- The page displays user's bookings, with options of cancellation and modification -->

<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
	xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" layout:decorate="~{template}"
	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity5">

<head>

	<meta name="_csrf" th:content="${_csrf.token}" />
	<meta name="_csrf_header" th:content="${_csrf.headerName}" />

	<script>
		$(document).ready(function () {

			$('.cancel-btn').on('click', function () {
				$('#confirmationModal').modal('show');
				var form = $(this).closest('.cancel-form');

				$('#confirmCancel').off('click').on('click', function () {
					form.submit();
				});
			});


			$('#modal').on('hidden.bs.modal', function (e) {
				window.location.reload();
			});


			$('.openInfoModalButton').on('click', function () {
				var bookingID = $(this).data('booking-id');

				$('#modal-body').load('/userBookingInfo?bookingID=' + bookingID, function () {
					$('#modal').modal('show');
				});
			});


			$('.modifyBtn').on('click', function () {


				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");


				console.log("x");
				var bookingID = $(this).data('booking-id');

				$.ajax({
					type: 'POST',
					url: '/userBookings/saveInSessionUserBookingID',
					data: {
						bookingID: bookingID
					},

					beforeSend: function (xhr) {
						xhr.setRequestHeader(header, token);
					},
					success: function (data) {

						window.location.href = data;
					},
					error: function (xhr, status, error) {
						console.error(error);
					}
				});
			});
		});
	</script>
</head>

<body>
	<div layout:fragment="content">
		<div class="container">
			<h2 class="fw-bold mb-4 text-center">Đặt Chỗ Của Tôi</h2>
			<p class="text-muted">
				Nhấn vào các nút để xem, chỉnh sửa hoặc hủy đặt chỗ của bạn.
			</p>
		</div>

		<section th:if="${!#lists.isEmpty(bookingsList)}">
			<div class="container">
				<div class="row">

					<div th:each="booking : ${bookingsList}" class="col-lg-5 col-md-6 mb-4">
						<div class="card rounded-0">
							<div class="card-header text-white" style="background-color:#ced4da">
								<div class="row">
									<div class="col">
										<h5 class="card-title mb-0">
											Đặt chỗ #<span th:text="${booking.id}"></span>
										</h5>
									</div>
									<div class="col d-flex justify-content-end">
										<form class="cancel-form"
											th:action="@{/userBookings/ProcessBookingCancellation}" method="post">
											<input type="hidden" name="bookingID" th:value="${booking.id}" />

											<button type="button"
												th:if="${booking.isCanceled == false
											            and booking.route != null
											            and booking.route.flights != null
											            and not booking.route.flights.isEmpty()
											            and !#strings.equals(booking.route.flights[0].statusID, T(com.example.SwipeFlight.server.utils.Constants).FLIGHT_STATUS_CANCELED)
											            and T(com.example.SwipeFlight.server.utils.DateTimeUtils).isNowBeforeGivenDateTimeWithOffset(booking.route.flights[booking.route.flights.size()-1].departureDate, booking.route.flights[booking.route.flights.size()-1].departureTime, 2)}"
												class="btn btn-outline-danger rounded-0 cancel-btn">
												<i class="fa-solid fa-circle-xmark"></i>
											</button>
										</form>
									</div>
								</div>
							</div>
							<div class="card-body">
								<ul class="list-group list-group-flush">
									<li class="list-group-item border-0"><strong>Vé:</strong><span class="float-end"
											th:text="${booking.numOfTickets}"></span></li>
									<li class="list-group-item border-0"><strong>Giá:</strong><span class="float-end"
											th:text="${'$' + #numbers.formatDecimal(booking.totalPrice, 0, 'COMMA', 0, 'POINT')}"></span>
									</li>
									<li class="list-group-item border-0"><strong>Ngày Đặt Chỗ:</strong><span
											class="float-end" th:text="${booking.bookingDate}"></span></li>
									<li class="list-group-item border-0"><strong>Ngày Sửa Đổi Cuối:</strong><span
											class="float-end" th:text="${booking.lastModifyDate}"></span></li>
									<li th:each="status : ${FlightStatusList}" class="list-group-item border-0"
										th:if="${#strings.equals(#strings.toString(booking.route.flights[0].statusID), #strings.toString(status.id))}">
										<strong>Trạng Thái Lộ Trình:</strong>
										<span class="float-end" th:if="${status.id == 1}"
											th:text="${status.description}"></span>
										<span class="float-end text-danger fw-bold" th:if="${status.id != 1}"
											th:text="${status.description}"></span>
									</li>
									<li class="list-group-item border-0">
										<strong>Trạng Thái Đặt Chỗ:</strong>
										<span th:if="${booking.isCanceled == true}"
											class="float-end text-danger fw-bold">Đã Hủy</span>
										<span th:if="${booking.isCanceled == false}" class="float-end">Hoạt Động</span>
									</li>
								</ul>
							</div>
							<div class="d-flex justify-content-center">
								<button type="button" class="btn btn-primary rounded-0 me-2 openInfoModalButton"
									th:data-booking-id="${booking.id}">
									<i class="fa-solid fa-circle-info me-2"></i> Chi Tiết
								</button>

								<button type="button"
									th:if="${booking.isCanceled == false
										            and booking.route != null
										            and booking.route.flights != null
										            and not booking.route.flights.isEmpty()
										            and !#strings.equals(booking.route.flights[0].statusID, T(com.example.SwipeFlight.server.utils.Constants).FLIGHT_STATUS_CANCELED)
										            and T(com.example.SwipeFlight.server.utils.DateTimeUtils).isNowBeforeGivenDateTimeWithOffset(booking.route.flights[booking.route.flights.size()-1].departureDate, booking.route.flights[booking.route.flights.size()-1].departureTime, 2)}"
									class="btn btn-primary rounded-0 me-2 modifyBtn" th:data-booking-id="${booking.id}">
									<i class="fa-regular fa-pen-to-square me-2"></i> Chỉnh Sửa
								</button>
							</div>
						</div>
					</div>

				</div>
			</div>
		</section>

		<section th:if="${#lists.isEmpty(bookingsList)}">
			<div class="container text-center">
				<br />
				<div class="alert alert-primary w-40" role="alert">
					Không có lộ trình nào.
				</div>
			</div>
		</section>

		<!-- Modal -->
		<div class="modal fade" id="modal" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog modal-xl modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-body" id="modal-body"></div>
				</div>
			</div>
		</div>

		<!-- Xác Nhận Hủy -->
		<div class="modal fade" id="confirmationModal" tabindex="-1" aria-labelledby="confirmationModalLabel"
			aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered">
				<div class="modal-content">
					<div class="modal-body">Bạn có chắc chắn muốn hủy đặt chỗ này không?</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">Không</button>
						<button type="button" class="btn btn-danger" id="confirmCancel">Có</button>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>

</html>