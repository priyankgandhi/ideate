angular.module('starter.controllers', [])

.controller('AppCtrl', function($scope, $ionicModal, $timeout) {

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

  $scope.navTitle='<img class="title-image" src="./img/genieLogo.png" />';

  var alternate,
    isIOS = ionic.Platform.isWebView() && ionic.Platform.isIOS();

  $scope.hideTime = true;
  $scope.loading = false;

  /* WHEN USER TYPES AND SENDS MESSAGE */
  $scope.sendMessage = function() {
    $scope.displayMessage(true, $scope.data.message);
    $scope.processCommand($scope.data.message);
    delete $scope.data.message;
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

    console.log(message);
    if(message.type == 'card')  {
      $scope.messages.push({
        type : 'card',
        userId: alternate ? '12345' : '54321',
        obj: message.obj,
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


    $ionicScrollDelegate.scrollBottom(true);
  }

  /* THIS IS WHERE THE PROCESSING WILL HAPPEN */
  $scope.processCommand = function(query) {
    $scope.loading = true;

    ApiAIService.fetch(query).then(function(response) {
      $scope.loading = false;
      if(response.data.result.fulfillment.data) {
          if(typeof response.data.result.fulfillment.data == 'object')  {
            var responseData = {
              "type" : "card",
              "obj" : response.data.result.fulfillment.data
            }
          }
          else {
            var responseData = {
              "type" : "card",
              "obj" : JSON.parse(response.data.result.fulfillment.data)
            }
          }
        $scope.displayMessage(false, responseData);
      }
      else {
        $scope.displayMessage(false, response.data.result.fulfillment.speech);
      }
    });
  }

  $scope.data = {};
  $scope.myId = '12345';
  $scope.messages = [];
});
