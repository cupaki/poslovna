(function (angular) {
  var app = angular.module('app');

  app.config(function($stateProvider) {
    $stateProvider
    .state('login', {
      url: '/login',
      templateUrl: '/Templates/login.html'
    });
  });
})(angular);
