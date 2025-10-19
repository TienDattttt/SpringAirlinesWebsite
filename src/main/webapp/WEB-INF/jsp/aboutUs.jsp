<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
	xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout" layout:decorate="~{template}"
	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity5">

<head>
	<meta charset="UTF-8">
	<title>Giới Thiệu Công Ty</title>
</head>

<body>
	<div layout:fragment="content">

		<!-- Phần Giới Thiệu -->
		<section class="py-3 py-md-5">
			<div class="container" style="margin-top: 40px; margin-bottom: 40px;">
				<div class="row gy-3 align-items-lg-center">
					<div class="col-12 col-lg-6">
						<img class="img-fluid" src="/images/general/vacation.png" alt="Vacation Image"
							style="border-radius: 12px; transition: all 0.3s ease-in-out; width: 100%; height: auto;">
					</div>
					<div class="col-12 col-lg-6">
						<h2 class="mb-4" style="color: #007bff; font-weight: 700; transition: all 0.3s ease;">Về Chúng
							Tôi</h2>
						<p class="lead fs-4" style="font-size: 1.25rem; line-height: 1.6; color: #6c757d;">FlyNow không
							chỉ là một hãng hàng không, mà là cửa ngõ đưa bạn đến với những chuyến đi thú vị.</p>
						<p style="font-size: 1rem; line-height: 1.6; color: #6c757d;">Ra đời với khát vọng đột phá trong
							ngành hàng không, FlyNow đã chính thức cất cánh từ năm 2024. Chúng tôi không ngừng cải tiến
							để mang lại trải nghiệm bay tốt nhất cho khách hàng.</p>
					</div>
				</div>
			</div>
		</section>

		<!-- Phần Sứ Mệnh và Giá Trị -->
		<section class="py-3 py-md-5" style="background-color: #f8f9fa;">
			<div class="container">
				<div class="row text-center">
					<div class="col-md-6">
						<div class="value-box" style="border: 1px solid #eaeaea; padding: 20px; border-radius: 12px; background-color: #ffffff; 
								box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1); transition: all 0.3s ease;">
							<h4 style="color: #343a40; font-weight: bold;">Sứ Mệnh Của Chúng Tôi</h4>
							<p style="font-size: 1rem; line-height: 1.6; color: #6c757d;">Kết nối mọi người, mọi nơi và
								mọi nền văn hóa qua những chuyến bay an toàn, tiện nghi và giá cả hợp lý.</p>
						</div>
					</div>
					<div class="col-md-6">
						<div class="value-box" style="border: 1px solid #eaeaea; padding: 20px; border-radius: 12px; background-color: #ffffff; 
								box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1); transition: all 0.3s ease;">
							<h4 style="color: #343a40; font-weight: bold;">Giá Trị Cốt Lõi</h4>
							<p style="font-size: 1rem; line-height: 1.6; color: #6c757d;">Chúng tôi luôn đổi mới và giữ
								vững tính chính trực trong mọi hoạt động, đặt khách hàng làm trung tâm.</p>
						</div>
					</div>
				</div>
			</div>
		</section>

		<!-- Phần Tham Gia -->
		<section class="py-3 py-md-5">
			<div class="container text-center" style="margin-top: 40px; margin-bottom: 40px;">
				<h2 style="color: #007bff; font-weight: 700; transition: all 0.3s ease;">Tham Gia Cùng Chúng Tôi</h2>
				<p class="lead mb-4" style="font-size: 1.25rem; line-height: 1.6; color: #6c757d;">Khám phá thế giới
					cùng FlyNow. Trải nghiệm sự khác biệt và tham gia cùng chúng tôi trong hành trình tiếp theo.</p>
				<a href="/bookingTrip" class="btn btn-primary" style="background-color: #007bff; border-color: #007bff; border-radius: 8px; 
						padding: 10px 20px; font-size: 1rem; font-weight: bold; transition: all 0.3s ease;">
					Tham Gia Ngay
				</a>
			</div>
		</section>

	</div>
</body>

</html>