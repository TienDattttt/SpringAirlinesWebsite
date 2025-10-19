<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity5">

<head>

	<script type="text/javascript" th:src="@{/js/add.js}"></script>

	<style>
		.checked {
			color: #FFC109;
			font-size: 30px;
		}

		.unchecked {
			font-size: 30px;
		}

		.fa-star {
			cursor: pointer;
		}
	</style>
	<script>
		$(document).ready(function () {


			$(".fa-star").click(function () {
				var rating = $(this).data("rating");
				var category = $(this).data("category");

				$(".fa-star[data-category='" + category + "']").removeClass("checked");

				$(this).addClass("checked").prevAll(".fa-star[data-category='" + category + "']").addClass("checked");

				$("#" + category + "RatingInput").val(rating);
			});
		});
	</script>
</head>

<body>

	<div th:if="${success_message == null}">
		<form id="addForm" th:action="@{/userFeedbackAdd/ProcessFeedbackForm}" method="post">

			<div class="row">
				<div class="col">
					<label class="m-4">Vệ sinh</label>
				</div>
				<div class="col mt-4 text-secondary">
					<span class="fa fa-star unchecked" data-rating="1" data-category="cleaning"></span> <span
						class="fa fa-star unchecked" data-rating="2" data-category="cleaning"></span> <span
						class="fa fa-star unchecked" data-rating="3" data-category="cleaning"></span> <span
						class="fa fa-star unchecked" data-rating="4" data-category="cleaning"></span> <span
						class="fa fa-star unchecked" data-rating="5" data-category="cleaning"></span>
				</div>
			</div>

			<div class="row">
				<div class="col">
					<label class="m-4">Sự tiện lợi</label>
				</div>
				<div class="col mt-4 text-secondary">
					<span class="fa fa-star unchecked" data-rating="1" data-category="convenience"></span> <span
						class="fa fa-star unchecked" data-rating="2" data-category="convenience"></span> <span
						class="fa fa-star unchecked" data-rating="3" data-category="convenience"></span> <span
						class="fa fa-star unchecked" data-rating="4" data-category="convenience"></span> <span
						class="fa fa-star unchecked" data-rating="5" data-category="convenience"></span>
				</div>
			</div>

			<div class="row">
				<div class="col">
					<label class="m-4">Dịch vụ</label>
				</div>
				<div class="col mt-4 text-secondary">
					<span class="fa fa-star unchecked" data-rating="1" data-category="service"></span> <span
						class="fa fa-star unchecked" data-rating="2" data-category="service"></span> <span
						class="fa fa-star unchecked" data-rating="3" data-category="service"></span> <span
						class="fa fa-star unchecked" data-rating="4" data-category="service"></span> <span
						class="fa fa-star unchecked" data-rating="5" data-category="service"></span>
				</div>
			</div>

			<input type="hidden" name="cleaningRating" id="cleaningRatingInput" value="0"> <input type="hidden"
				name="convenienceRating" id="convenienceRatingInput" value="0"> <input type="hidden"
				name="serviceRating" id="serviceRatingInput" value="0"> <input type="hidden" name="routeNumber"
				th:value="${routeNumber}">

			<div class="text-center mt-4">
				<button type="submit" class="btn btn-primary rounded-0">Đăng</button>
			</div>
			<span th:if="${error_message != null}" class="text-danger" th:text="${error_message}"> </span>

		</form>
	</div>
	<div th:if="${success_message != null}" class="text-success">
		<i class="fa-solid fa-circle-check me-2"></i><span th:text="${success_message}"> </span>
	</div>


</body>

</html>