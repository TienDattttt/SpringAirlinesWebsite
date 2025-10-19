<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
	xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" layout:decorate="~{template}"
	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity5">

<head>
	<style>
		/* Tùy chỉnh toàn bộ phần gallery */
		body {
			font-family: 'Arial', sans-serif;
			background-color: #f8f9fa;
			margin: 0;
			padding: 0;
		}

		/* Phong cách của vùng gallery */
		#gallery {
			padding: 50px 0;
		}

		.container {
			max-width: 1200px;
			margin: 0 auto;
		}

		.card {
			border: none;
			overflow: hidden;
			transition: all 0.3s ease-in-out;
			background-color: #ffffff;
			border-radius: 12px;
		}

		.card:hover {
			box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
			transform: translateY(-10px);
		}

		.card img {
			width: 100%;
			height: 200px;
			object-fit: cover;
			transition: all 0.3s ease-in-out;
			border-radius: 12px 12px 0 0;
		}

		.card img:hover {
			transform: scale(1.1);
		}

		.card-body {
			padding: 20px;
			text-align: center;
		}

		.card-title {
			font-size: 1.25rem;
			font-weight: 600;
			color: #333;
			margin-bottom: 10px;
		}

		.card-text {
			font-size: 0.95rem;
			color: #555;
			margin-bottom: 15px;
		}

		.card-text.limited {
			display: -webkit-box;
			-webkit-box-orient: vertical;
			-webkit-line-clamp: 4;
			line-clamp: 4;
			overflow: hidden;
		}

		.btn-outline-danger,
		.btn-outline-primary {
			transition: all 0.3s ease-in-out;
			font-weight: bold;
			border-width: 2px;
			border-radius: 8px;
		}

		.btn-outline-danger {
			color: #dc3545;
			border-color: #dc3545;
		}

		.btn-outline-danger:hover {
			background-color: #dc3545;
			color: #fff;
		}

		.btn-outline-primary {
			color: #007bff;
			border-color: #007bff;
		}

		.btn-outline-primary:hover {
			background-color: #007bff;
			color: #fff;
		}

		.d-grid {
			margin-top: 10px;
		}

		/* Responsive cho màn hình nhỏ hơn */
		@media (max-width: 768px) {
			.card img {
				height: 150px;
			}

			.card-body {
				padding: 15px;
			}

			.card-title {
				font-size: 1.1rem;
			}

			.card-text {
				font-size: 0.9rem;
			}

			.btn {
				font-size: 0.9rem;
			}
		}

		@media (max-width: 576px) {
			.card img {
				height: 120px;
			}
		}
	</style>
</head>

<body>
	<div layout:fragment="content">
		<section id="gallery">
			<div class="container">
				<div class="row">
					<div th:each="city : ${citiesList}" class="col-lg-3 col-md-6 mb-4">
						<div class="card">
							<img class="card-img-top" th:src="${city.imgUrl}" alt="City Image" />

							<div class="card-body">
								<h5 class="card-title" th:text="${city.name}">Tên thành phố</h5>
								<p class="card-text limited" th:text="${city.description}">Mô tả về thành phố này</p>

								<!-- Nếu là admin thì hiện nút chỉnh sửa -->
								<div th:if="${sessionUser != null && sessionUser.isAdmin == true}">
									<form th:action="@{/adminCity}" method="post">
										<input type="hidden" name="cityID" th:value="${city.id}" />
										<div class="d-grid gap-2">
											<button type="submit" class="btn btn-outline-danger">Chỉnh sửa</button>
										</div>
									</form>
								</div>

								<!-- Nếu không phải admin thì hiện nút Đọc thêm -->
								<div th:if="${sessionUser == null || sessionUser.isAdmin == false}">
									<form th:action="@{/city}" method="post">
										<input type="hidden" name="cityID" th:value="${city.id}" />
										<div class="d-grid gap-2">
											<button type="submit" class="btn btn-outline-primary">Đọc thêm</button>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div> <!-- end col -->
				</div> <!-- end row -->
			</div> <!-- end container -->
		</section>
	</div>
</body>

</html>