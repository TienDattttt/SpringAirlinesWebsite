<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
	xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" layout:decorate="~{template}"
	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity5">

<head>
</head>

<body>
	<div layout:fragment="content">
		<div class="container">
			<h2 class="fw-bold mb-4 text-center">Thông tin Booking</h2>
			<p class="text-muted">
				Trang hiển thị tất cả các thông tin của vé được đặt.

			</p>
		</div>


		<section>
			<div class="container">
				<form th:action="@{/adminBookings}" class="mb-4">
					<div class="row">
						<div class="col-md-2">
							<input type="number" class="form-control" name="bookingID" placeholder="Booking ID"
								th:value="${param.bookingID}">
						</div>
						<div class="col-md-2">
							<input type="number" class="form-control" name="userID" placeholder="User ID"
								th:value="${param.userID}">
						</div>
						<div class="col-md-2">
							<input type="checkbox" class="btn-check" id="btn-check-4" autocomplete="off"
								name="isCanceled" value="true"
								th:checked="${#strings.equals(param.isCanceled, 'true')}">
							<label class="btn" for="btn-check-4">Đã hủy</label>
						</div>
					</div>
					<div class="row mt-3 d-flex justify-content-center text-center">
						<div class="col-md-3 mb-5">
							<button type="submit"
								class="btn btn-primary btn-lg rounded-pill shadow-sm text-white px-4 py-2"
								style="background-color: #0275d8; border: none; transition: all 0.3s ease;">
								<i class="fa-solid fa-search me-2"></i> Tìm kiếm
							</button>
						</div>

					</div>
				</form>
			</div>
		</section>

		<section th:if="${!bookingsList.isEmpty()}">
			<div class="container" style="align-items: center;">
				<table id="table" class="table table-hover">
					<thead>
						<tr>
							<th>Booking ID</th>
							<th>User ID</th>
							<th>Số vé</th>
							<th>Tổng Giá</th>
							<th>Ngày Đặt Vé</th>
							<th>Chỉnh Sửa Cuối</th>
							<th>Trạng Thái</th>

						</tr>
					</thead>
					<tbody>
						<tr th:each="booking : ${bookingsList}">
							<td><span th:text="${booking.id}"></span></td>
							<td><span th:text="${booking.userID}"></span></td>
							<td><span th:text="${booking.numOfTickets}"></span>
							</td>
							<td><span
									th:text="${'$' + #numbers.formatDecimal(booking.totalPrice, 0, 'COMMA', 0, 'POINT')}"></span>
							</td>
							<td><span th:text="${booking.bookingDate}"></span>
							</td>
							<td><span th:text="${booking.lastModifyDate}"></span>
							</td>
							<td>
								<button disabled type="button" th:if="${booking.isCanceled == true}"
									class="btn btn-danger rounded-0">Đã hủy
								</button>
								<button disabled type="button" th:if="${booking.isCanceled == false}"
									class="btn btn-success rounded-0">Hoạt động
								</button>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</section>

		<section th:if="${bookingsList.isEmpty()}">
			<br />
			<div class="container text-center">
				<div class="alert alert-primary w-40" role="alert">
					Không có đặt chỗ nào tồn tại cho các bộ lọc đã cho. </div>
			</div>
		</section>

	</div>
</body>

</html>