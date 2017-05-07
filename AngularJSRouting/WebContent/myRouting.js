(function() {

	var routingApplication = angular
			.module("routingApplication", [ "ngRoute" ]);

	routingApplication.config(function($routeProvider) {

		$routeProvider.when("/", {
			templateUrl : "main.htm",
			controller : "homeController"
		}).when("/about", {
			templateUrl : "main.htm",
			controller : "aboutController"
		}).when("/contact", {
			templateUrl : "main.htm",
			controller : "contactController"
		}).when("/books", {
			templateUrl : "books.htm",
			controller : "booksController"
		}).otherwise({
			redirectTo : "/"
		})

	});

	
	routingApplication.controller("booksController", function($scope, $http) {
		$scope.title = "Books";
		
		var book = {
			id : 0,
			title : "",
			author : ""
		};
		$scope.book = book;

		var books = [];
		$scope.books = books;

		$scope.removeBook = function(index) {
			
			//här tar vi bort boken från listan på klientsidan:
			$scope.books.splice(index, 1); 
			
			//Det som återstår är att ta bort boken i databasen, 
			//och tillhörande funktionalitet för det.
		};

		var onBooksComplete = function(response) {
			$scope.books = response.data;
		}

		var onAddBookComplete = function(response) {
			$scope.description = "Added new book to list " + response.data;
			
			//här lägger vi till boken i listan på klientsidan:
			var tmp = angular.copy(book);
			$scope.books.push(tmp);
			$scope.book.title = "";
			$scope.book.author = "";

		}

		var onError = function(reason) {
			$scope.title = "Could not fetch data " + reason.status;
		}

		$http.get("GetBooks").then(onBooksComplete, onError);

		$scope.addBook = function() {
			// alert($scope.bookTitle + $scope.bookAuthor);
			var uri = "AddBook?title=" + $scope.book.title + "&author="
					+ $scope.book.author;
			alert(uri);
			$http.get(uri).then(onAddBookComplete, onError);

		}

	});

	routingApplication
			.controller(
					"homeController",
					function($scope) {
						$scope.title = "Home";
						$scope.description = "This is the home page, presented by angular routing";
					});
	routingApplication
			.controller(
					"aboutController",
					function($scope) {
						$scope.title = "About";
						$scope.description = "This is the about page, presented by angular routing";
					});
	routingApplication
			.controller(
					"contactController",
					function($scope) {
						$scope.title = "Contact";
						$scope.description = "This is the contact page, presented by angular routing";
					});

}());