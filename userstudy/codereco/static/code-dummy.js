angular.module('codereco',['ui.codemirror', 'angular-input-stars'])
  .controller('AppController', ['$scope', function($scope) {
    
    $scope.searched = false;
    $scope.weighted = false;
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
    // $scope.baseUrl="http://ec2-34-208-177-148.us-west-2.compute.amazonaws.com";
    $scope.baseUrl="http://localhost";
    $scope.serviceUrl= $scope.baseUrl + ":8080";   
    $scope.studyUrl= $scope.baseUrl + ":80";    
    $scope.ratings = {};
    $scope.voteCount = 0;
    $scope.voteTotal = 15;

    $scope.update = function update(results){
        $scope.ratings = {};
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
			$scope.error = "";
		}
        else{
            console.log(results);
            $scope.error = "Check for compilation errors.. (semicolons, keywords or just comment everything and try uncommenting one by one)";
        }
		$scope.$apply();
    };

    $scope.postRequests = function postRequests(){
        $scope.searched = true;
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

     $scope.postSimRequest = function(){
        $scope.weighted = true;
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

        /*$.ajax({
            url: $scope.studyUrl + '/weightupdate',
			headers : {'weights': JSON.stringify($scope.weights)},
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            crossDomain: true
        });*/
    }

    $scope.rate = function(error, reco){
        var key = reco.source + "," + reco.rank;
        if (!(key in $scope.ratings)){
            $scope.ratings[key] = reco.rating;
            $scope.voteCount += 1;
        }
        /*var vote = new Object();
        vote.qId = qId;
        vote.reco = reco;
        $scope.voteCount += 1;
        // console.log(vote);
        $.ajax({
            url: $scope.studyUrl + '/vote',
			headers : {'weights': JSON.stringify($scope.weights)},
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: angular.toJson(vote),
            success: function(data){
                var key = reco.source + "," + reco.rank;
                // Buggy because different recos come for same source and rank
                // $scope.ratings[key] = reco.rating;
            }
        });*/
    }

	$scope.submit = function(){
	  /*var reqData = new Object();
	  reqData.questionId = qId;
	  reqData.text = $scope.methodText;
	  if (confirm('Are you sure? You cannot come back to this question later.')) {
   	     $.ajax({
            url: $scope.studyUrl + '/submit',
			headers : {'weights': JSON.stringify($scope.weights)},
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: angular.toJson(reqData),
            success: function(data){
            	window.location.replace($scope.studyUrl);
            }
        });
	  }*/
	};

	$scope.log = function(reco){
	    /*console.log(reco);
        var vote = new Object();
        vote.qId = qId;
        vote.reco = reco;
        $scope.voteCount += 1;
        $.ajax({
            url: $scope.studyUrl + '/cutcopy',
			headers : {'weights': JSON.stringify($scope.weights)},
            type: 'POST',
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: angular.toJson(vote)
        });*/
    }
    
    angular.element(document).ready(function () {
        $('#signatureSlider').on('slideStop', $scope.postSimRequest).data('slider');
        $('#structureSlider').on('slideStop', $scope.postSimRequest).data('slider');
        $('#conceptsSlider').on('slideStop', $scope.postSimRequest).data('slider');
        $('#languageSlider').on('slideStop', $scope.postSimRequest).data('slider');
    });
   
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
