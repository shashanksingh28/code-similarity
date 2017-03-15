angular.module('codereco',['ui.codemirror'])
  .controller('AppController', ['$scope', function($scope) {
    $scope.count = 0;
    $scope.methodText = "";  
    $scope.editorOptions={
        mode:"text/x-java",
        matchBrackets: true,
		autoCloseBrackets : true,
        lineNumbers: true,
        extraKeys: {
            "Enter": $scope.postRequests
        }
    };

    $scope.textRecos = null;
    $scope.proposedRecos = null;
    $scope.weights = {'language': 1, 'structure': 1, 'signature': 1,'concepts': 1};
    // $scope.baseUrl="http://ec2-35-163-170-172.us-west-2.compute.amazonaws.com";
    $scope.baseUrl="http://localhost"
    $scope.serviceUrl= $scope.baseUrl + ":5000";   
    $scope.studyUrl= $scope.baseUrl + ":8000";   
    
    $scope.ratings = {};

    $scope.update = function update(results){
        if(Object.prototype.toString.call(results) === '[object Array]') {
        	for(var i = 0; i < results.length; i++){
				// If we have seen a rating before, don't loose it
				if (results[i].text in $scope.ratings){
				    results[i].rating == $scope.ratings[results[i].text];
				}
                else{
				    results[i].rating = 1;
                }
			}
			$scope.mixRecos = results;
			$scope.$apply();
		}
        else{
            console.log(results);
        }
    };

    $scope.postRequests = function postRequests(){
        $.ajax({
            url: $scope.serviceUrl + '/mix',
			headers : {'weights': JSON.stringify($scope.weights)},
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            crossDomain: true,
            data: $scope.methodText,
            success: $scope.update
        });
    };

     $scope.postSimRequest = function(model){
		$scope.count += 1;
		if($scope.count % 3 != 0){
			return;
		}
        $.ajax({
            url: $scope.serviceUrl + '/mix',
			headers : {'weights': JSON.stringify($scope.weights)},
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            crossDomain: true,
            data: $scope.methodText,
            success: $scope.update
        });
    }

    $scope.rate = function(newRating, reco){
        var vote = new Object();
        // Hack alert! Wasn't reflecting automatically
        if (reco.rating == newRating){
            return;
        }
        reco.rating = newRating;
        vote.qId = qId;
        vote.reco = reco;
        console.log(vote);
        $.ajax({
            url: $scope.studyUrl + '/vote',
			headers : {'weights': JSON.stringify($scope.weights)},
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            crossDomain: true,
            data: angular.toJson(vote),
            success: function(){
                 $scope.ratings[reco.text] = reco.rating;
            }
        });
    }

	$scope.submit = function(){
	  if (confirm('Are you sure you want to save this thing into the database?')) {
   	     $.ajax({
            url: $scope.serviceUrl + '/finish',
			headers : {'weights': JSON.stringify($scope.weights)},
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            crossDomain: true,
            data: qId
        });
	  }
	}; 
  }])
  .directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.ngEnter);
                }); 
                event.preventDefault();
            }
        });
    };
  })
  .directive('starRating', function(){
    return {
        restrict: 'A',
        template: '<ul class="rating">' +
            '<li ng-repeat="star in stars" ng-class="star" ng-click="toggle($index)">' +
            '\u2605' +
            '</li>' +
            '</ul>',
        scope: {
            ratingValue: '=',
            max: '=',
            onRatingSelected: '&'
        },
        link: function (scope, elem, attrs) {
            var updateStars = function () {
                scope.stars = [];
                for (var i = 0; i < scope.max; i++) {
                    scope.stars.push({
                        filled: i < scope.ratingValue
                    });
                }
            };
            scope.toggle = function (index) {
                scope.ratingValue = index + 1;
                scope.onRatingSelected({
                    rating: index + 1
                });
            };
            scope.$watch('ratingValue', function (oldVal, newVal) {
                if (newVal) {
                    updateStars();
                }
            });
        }
    }
  });
