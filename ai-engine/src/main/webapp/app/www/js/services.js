app.factory('OpenTableService', function($http) {
   return {
	   getPlaces: function() {
		   return $http.get('http://opentable.herokuapp.com/api/restaurants/107257')
           	.then(function(response) {
           		return response.data;
            }, function error(response) {
  	          console.log('error from open table')
    	    });
        }
   }
});

app.factory('ApiAIService', function($http) {
   return {
	   fetch: function(query) {
		    return $http({
          method : 'GET',
          headers: {
            'Authorization': 'Bearer e6f74f658a0f4831a54b09a95ef2437e',
          },
          url : 'https://api.api.ai/v1/query?v=20150910&lang=en&sessionId=1234567890&query=' + query
          //data: angular.toJson(offerPostDataObj),
          }).then(function succes(response) {
            console.log('Success')
            return response;
          }, function error(response) {
                console.log('API AI Server side error')
          });
     }
   }
});

app.factory('YelpService', function($http) {
   return {
	   search: function() {
		   return $http.get('https://api.yelp.com/v3/businesses/search?term=italian+food&location=94070&radius=10&pricing_filter=3')
           	.then(function(response) {
           		return response.data;
            }, function error(response) {
  	          console.log('error from yelp')
    	    });
        }
   }
});
