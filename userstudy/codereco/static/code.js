angular.module('codereco',['ui.codemirror', 'angular-input-stars'])
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
    $scope.serviceUrl= $scope.baseUrl + ":8080";   
    $scope.studyUrl= $scope.baseUrl + ":80";    
    $scope.ratings = {};

    $scope.update = function update(results){
        if(Object.prototype.toString.call(results) === '[object Array]') {
        	for(var i = 0; i < results.length; i++){
        		var key = results[i].source + "," + results[i].rank;
				// If we have seen a rating before, don't loose it
				if (key in $scope.ratings){
				    results[i].rating = $scope.ratings[key];
				}
                else{
				    results[i].rating = 0;
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

    $scope.rate = function(error, reco){
        var vote = new Object();
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
            success: function(data){
                var key = reco.source + "," + reco.rank;
                $scope.ratings[key] = reco.rating;
            }
        });
    }

	$scope.submit = function(){
	  var reqData = new Object();
	  reqData.questionId = qId;
	  reqData.text = $scope.methodText;
	  if (confirm('Are you sure? You cannot come back to this question later.')) {
   	     $.ajax({
            url: $scope.studyUrl + '/submit',
			headers : {'weights': JSON.stringify($scope.weights)},
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            crossDomain: true,
            data: angular.toJson(reqData),
            success: function(data){
            	console.log("Done");
            }
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
  });
