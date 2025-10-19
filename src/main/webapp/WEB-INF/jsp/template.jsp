<!-- The page is the main template of the web-app (displays logo, search and menu) -->

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
	xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout">

<head>
	<meta charset="UTF-8">

	<!-- bootstrap, font-awesome, bootstrap-icons -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" />
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" />
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

	<!-- auto-complete -->
	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.js"></script>
	<link rel="stylesheet" type="text/css" media="screen"
		href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/themes/base/jquery-ui.css" />

	<!-- bootstrap scripts for modal -->
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"></script>

	<!--  using Flatpickr library for datepicker  -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
	<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

	<link rel="stylesheet"
		href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/css/bootstrap-select.min.css">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>


	<!-- css files -->
	<link th:href="@{/css/general.css}" rel="stylesheet" />

	<script type="text/javascript">
		$(document).ready(function () {

			$('.searchInput').autocomplete({

				source: '/citySearch/fetchSuggestionsForCitySearch',

				select: function (event, ui) {
					var selectedCityFromSuggestions = ui.item.value;
					window.location.href = '/home?selectedCityFromSuggestions=' + selectedCityFromSuggestions;
				}
			});
		});
	</script>
</head>

<body>

	<!-- Header -->
	<header class="fixed-top">


		<section>
			<div class="text-center bg-image p-4"
				style="background-image: url('/images/general/plane-2.png'); background-position: center 2%; background-repeat: no-repeat;">

				<nav class="navbar navbar-expand-lg bg-body-tertiary" style="background-color: white">

					<div class="container">
						<a class="navbar-brand text-primary fw-bolder"> <img src="/images/general/logo.png" height="30"
								style="margin-top: -1px;" />
						</a>
						<button data-mdb-collapse-init class="navbar-toggler" type="button"
							data-mdb-target="#navbarButtonsExample" aria-controls="navbarButtonsExample"
							aria-expanded="false" aria-label="Toggle navigation">
							<i class="fas fa-bars"></i>
						</button>
						<div th:if="${sessionUser == null or sessionUser.isAdmin == false}"
							class="collapse navbar-collapse">
							<ul class="navbar-nav me-auto mb-2 mb-lg-0">
								<li class="nav-item">
									<a class="nav-link active" href="/home" style="color: #0275d8;">Trang Chủ</a>
								</li>
								<li class="nav-item">
									<hr class="d-lg-none" style="border-color: #0275d8;" />
									<span class="nav-link d-none d-lg-block mx-1" style="color: #0275d8;">|</span>
								</li>
								<li class="nav-item">
									<a class="nav-link" href="/aboutUs" style="color: #0275d8;">Giới Thiệu</a>
								</li>
								<li class="nav-item">
									<hr class="d-lg-none" style="border-color: #0275d8;" />
									<span class="nav-link d-none d-lg-block mx-1" style="color: #0275d8;">|</span>
								</li>
								<li class="nav-item">
									<a class="nav-link" href="/bookingTrip" style="color: #0275d8;">Đặt Vé</a>
								</li>
							</ul>
						</div>

						<div class="collapse navbar-collapse" id="navbarButtonsExample">
							<!-- Left links -->
							<ul class="navbar-nav me-auto mb-lg-0">
							</ul>
							<div th:if="${sessionUser == null}" class="d-flex align-items-center">
								<div class="d-flex align-items-center">
									<a href="/login" class="btn btn-primary me-2"
										style="transition: all 0.2s ease-in-out;">Đăng Nhập</a>
									<a href="/registration" class="btn btn-outline-primary"
										style="transition: all 0.2s ease-in-out;">Đăng Ký</a>
								</div>
							</div>
							<div th:if="${sessionUser != null}" class="d-flex align-items-center">
								<div th:if="${sessionUser.isAdmin == true}">
									<div class="dropdown">
										<button
											class="dropdown-toggle btn btn-outline-primary border-0 rounded shadow-sm"
											type="button" data-toggle="dropdown" aria-haspopup="true"
											aria-expanded="false" style="transition: all 0.2s ease-in-out;">
											<i class="fa-solid fa-circle-user me-2"></i> <span
												th:text="${sessionUser.userName}"></span>
										</button>
										<div class="dropdown-menu main-menu">
											<a class="dropdown-item main-item" href="/adminAddFlight">Thêm Chuyến
												Bay</a> <a class="dropdown-item main-item" href="/adminRoutes">Quản lý
												chuyến bay
											</a> <a class="dropdown-item main-item" href="/adminFeedbacks">Xem Phản
												Hồi</a>
											<a class="dropdown-item main-item" href="/adminBookings">Xem Đặt Vé</a>
											<a class="dropdown-item main-item" href="/home">Quản lý bài
												viết</a>
										</div>
									</div>
								</div>
								<div th:if="${sessionUser.isAdmin == false}">
									<div class="dropdown">
										<button
											class="dropdown-toggle btn btn-outline-primary border-0 rounded shadow-sm"
											type="button" data-toggle="dropdown" aria-haspopup="true"
											aria-expanded="false" style="transition: all 0.2s ease-in-out;">
											<i class="fa-solid fa-circle-user me-2"></i> <span
												th:text="${sessionUser.userName}"></span>
										</button>

										<div class="dropdown-menu main-menu">
											<a class="dropdown-item main-item" href="/userBookings">Vé của tôi</a> <a
												class="dropdown-item main-item" href="/userFlightCountdown">Chuyến bay
												sắp tới</a> <a class="dropdown-item main-item"
												href="/userFeedbackAvailability">Biểu Mẫu Phản Hồi</a>
										</div>
									</div>
								</div>
								<form th:action="@{/logout/ProcessUserLogout}" method="post">
									<button type="submit" data-mdb-ripple-init class="btn btn-outline-primary"
										style="transition: all 0.2s ease-in-out;">Đăng xuất</button>
								</form>
							</div>
						</div>
					</div>
				</nav>

				<br /> <br /> <br />
				<br /> <br /> <br />
				<br />

			</div>
		</section>


		<section>
			<div th:if="${sessionUser == null or sessionUser.isAdmin == false}" class="d-flex">
				<div class="searchBox">
					<input class="searchInput" type="text" name="" placeholder="Bạn muốn đi du lịch ở đâu?"
						th:value="${citySearch}" aria-label="Search" aria-describedby="search-addon">
					<button class="searchButton">
						<i class="fa-solid fa-magnifying-glass"></i>
					</button>
				</div>
			</div>
		</section>
	</header>

	<!-- Content: Dynamic -->
	<div layout:fragment="content" class="page-content"></div>

</body>

</html>