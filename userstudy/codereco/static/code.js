angular.module('codereco',['ui.codemirror'])
  .controller('AppController', ['$scope', function($scope) {
    
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
    $scope.baseUrl="http://localhost:5000";   

    $scope.update = function update(results){
        if(Object.prototype.toString.call(results) === '[object Array]') {
        	$scope.mixRecos = results;
			$scope.$apply();
		}
        else{
            console.log(results);
        }
    };

    $scope.postRequests = function postRequests(){
		
        $.ajax({
            url: $scope.baseUrl + '/mix',
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
         $.ajax({
            url: $scope.baseUrl + '/mix',
			headers : {'weights': JSON.stringify($scope.weights)},
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            crossDomain: true,
            data: $scope.methodText,
            success: $scope.update
        });
    }

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
