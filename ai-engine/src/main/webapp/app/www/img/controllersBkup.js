angular.module('starter.controllers', [])

.controller('AppCtrl', function($scope, $ionicModal, $timeout) {

  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //$scope.$on('$ionicView.enter', function(e) {
  //});

  // Form data for the login modal
  $scope.loginData = {};

  // Create the login modal that we will use later
  $ionicModal.fromTemplateUrl('templates/login.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.modal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeLogin = function() {
    $scope.modal.hide();
  };

  // Open the login modal
  $scope.login = function() {
    $scope.modal.show();
  };

  // Perform the login action when the user submits the login form
  $scope.doLogin = function() {

    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {
      $scope.closeLogin();
    }, 1000);
  };
})

.controller('PlaylistsCtrl', function($scope) {
  $scope.playlists = [
    { title: 'Reggae', id: 1 },
    { title: 'Chill', id: 2 },
    { title: 'Dubstep', id: 3 },
    { title: 'Indie', id: 4 },
    { title: 'Rap', id: 5 },
    { title: 'Cowbell', id: 6 }
  ];
})

.controller('PlaylistCtrl', function($scope, $stateParams) {
});

app.controller('BenefitsCtrl', function($scope, $timeout, $ionicScrollDelegate) {

  $scope.benefitsList = [
    {
      'type' : 'LoungeAccess',
      'label' : 'Priority Lounge Access',
      'icon' : 'ion-wineglass',
      'images' : [
        {'url' : 'https://d2llguf9uoxb71.cloudfront.net/lounge-media/image/JFK-001.jpg'},
        {'url' : 'https://d2llguf9uoxb71.cloudfront.net/lounge-media/image/JFK-002.jpg'},
        {'url' : 'https://d2llguf9uoxb71.cloudfront.net/lounge-media/image/JFK-003.jpg'}
      ],
      'lounges' : [
        {
          'name' : 'KAL Business Class Lounge',
          'hours' : '14:00 - 20:30 daily',
          'location' : 'Airside - after Security Checkpoint, the lounge is located on the right hand side, across Gate 3. All passengers must hold a boarding pass departing from Terminal 1.'
        }
      ]
    },
    {
      'type' : 'Rental Insurance',
      'label' : 'VIP Rental Car Insurance',
      'icon' : 'ion-model-s',
      'images' : [
        {'url' : 'http://park-place-hotel.com/assets/uploads/photo-gallery/beacon-lounge-horz.jpg'},
        {'url' : 'http://www.majestiktour.com/en/images/slider/img2.jpg'}
      ]
    }
  ]

});


/* CHAT CONTROLLER */
app.controller('ChatCtrl', function($scope, $timeout, $ionicScrollDelegate, OpenTableService, YelpService, ApiAIService, $http) {


  var aiConfig = {
      server: 'wss://api.api.ai:4435/api/ws/query',
      serverVersion: '20150910', // omit 'version' field to bind it to '20150910' or use 'null' to remove it from query
      token: 'e6f74f658a0f4831a54b09a95ef2437e',// Use Client access token there (see agent keys).
      sessionId: 'abcd',
      onInit: function () {
          console.log("> ON INIT use config");
      }
  };
  var apiAi = new ApiAi(aiConfig);

  apiAi.onInit = function () {
      console.log("> ON INIT use direct assignment property");
      apiAi.open();
  };

  apiAi.sendJson('hi');

  // $http({
  //       method : 'GET',
  //       headers: {
  //         'Authorization': 'Bearer e6f74f658a0f4831a54b09a95ef2437e',
  //       },
  //       url : 'https://api.api.ai/v1/query?v=20150910&query=lounge&lang=en&sessionId=1234567890'
  //       //data: angular.toJson(offerPostDataObj),
  //   }).then(function succes(response) {
  //     console.log('Success')
  //     console.log(response);
  //   }, function error(response) {
  //         console.log('Server side error')
  //
  //   });



  // $http.get('https://api.api.ai/v1/query?v=20150910&query=weather&timezone=Europe/Paris&lang=en&contexts=weather&contexts=europe&latitude=37.459157&longitude=-122.17926&sessionId=234234')
  //      .then(function(response) {
  //        return response.data;
  //      }, function error(response) {
  //        console.log('error from yelp')
  //    });



  ApiAIService.fetch().then(function(response) {
    console.log(response);
  });




  $scope.hideTime = true;
  var alternate,
    isIOS = ionic.Platform.isWebView() && ionic.Platform.isIOS();

  $scope.sendMessage = function() {

    $scope.displayMessage(true, $scope.data.message);
    $scope.processCommand($scope.data.message);

    delete $scope.data.message;
    $ionicScrollDelegate.scrollBottom(true);
  };


  $scope.inputUp = function() {
    if (isIOS) $scope.data.keyboardHeight = 216;
    $timeout(function() {
      $ionicScrollDelegate.scrollBottom(true);
    }, 300);
  };

  $scope.inputDown = function() {
    if (isIOS) $scope.data.keyboardHeight = 0;
    $ionicScrollDelegate.resize();
  };

  $scope.closeKeyboard = function() {
    // cordova.plugins.Keyboard.close();
  };

  /* DISPLAY MESSAGE */
  $scope.displayMessage = function(alt, message) {
    alternate = alt;
    var d = new Date();
    d = d.toLocaleTimeString().replace(/:\d+ /, ' ');

    if(message.type == 'card' ) {
      $scope.messages.push({
        type : 'card',
        userId: alternate ? '12345' : '54321',
        obj: message,
        time: d
      });
    }
    else {
      $scope.messages.push({
        type : 'text',
        userId: alternate ? '12345' : '54321',
        text: message,
        time: d
      });
    }

  }

  /* THIS IS WHERE THE PROCESSING WILL HAPPEN */
  $scope.processCommand = function(command) {

    var chatBubble;

    /* OPEN TABLE */
    if(command.indexOf('eat') !== -1)  {
      OpenTableService.getPlaces().then(function(placesList) {
        chatBubble = {
          'type' : 'card',
          'category' : 'OpenTable',
          'restaurant' : placesList
        };

        console.log(chatBubble)
        $scope.displayMessage(false, chatBubble);
      });
    }


    /* LOUNGES */
    else if(command.indexOf('lounge') !== -1) {
      var chatBubble = {
        'type' : 'card',
        'category' : 'Lounge',
        'label' : 'Priority Lounge Access',
        'icon' : 'ion-wineglass',
        'images' : [
          {'url' : 'https://d2llguf9uoxb71.cloudfront.net/lounge-media/image/JFK-001.jpg'},
          {'url' : 'https://d2llguf9uoxb71.cloudfront.net/lounge-media/image/JFK-002.jpg'},
          {'url' : 'https://d2llguf9uoxb71.cloudfront.net/lounge-media/image/JFK-003.jpg'}
        ],
        'lounge' : {
            'name' : 'KAL Business Class Lounge',
            'hours' : '14:00 - 20:30 daily',
            'location' : 'Airside - after Security Checkpoint, the lounge is located on the right hand side, across Gate 3. All passengers must hold a boarding pass departing from Terminal 1.'
          }
      }
      $scope.displayMessage(false, chatBubble);
    }
    else {
      chatBubble = command;
      $scope.displayMessage(false, chatBubble);
    }

  }

  $scope.data = {};
  $scope.myId = '12345';
  $scope.messages = [];
});
