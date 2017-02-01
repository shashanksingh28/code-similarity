angular.module('similarityApp', ['ui.codemirror'])
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
    $scope.ratio = 0.5;
    $scope.baseUrl="http://ec2-35-167-88-109.us-west-2.compute.amazonaws.com";

    $scope.updateText = function updateText(results){
    	// console.log("Text:");
        // console.log(results);
        if(Object.prototype.toString.call(results) === '[object Array]') {
        	$scope.textRecos = results;
			$scope.$apply();
		}
    }

    $scope.updateJaccard = function updateJaccard(results){
        // console.log("Jaccard");
        // console.log(results);
        if(Object.prototype.toString.call(results) === '[object Array]') {
        	$scope.jaccardRecos = results;
			$scope.$apply();
		}
    };

    $scope.updateProposed = function updateProposed(results){
        // console.log("Proposed:");
        // console.log(results);
        if(Object.prototype.toString.call(results) === '[object Array]') {
        	$scope.proposedRecos = results;
			$scope.$apply();
		}
    };

    $scope.postRequests = function postRequests(){
        $.ajax({
            url: $scope.baseUrl + '/equalJaccard',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            crossDomain: true,
            data: $scope.methodText,		
            success: $scope.updateJaccard
        });
        
        $.ajax({
            url: $scope.baseUrl + '/cosine',
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            crossDomain: true,
            data: $scope.methodText,
            success: $scope.updateText
        });
		
        $.ajax({
            url: $scope.baseUrl + '/simcode',
			headers : {'nl_ratio': $scope.ratio},
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            crossDomain: true,
            data: $scope.methodText,
            success: $scope.updateProposed
        });
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
