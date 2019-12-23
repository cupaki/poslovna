(function(angular) {
  var app = angular.module('app');

  var navigationController = function($scope, DatabaseService, $state) {

    DatabaseService.getTablesName(function(result) {
      $scope.tables = result.data;
    });

    $scope.showTable = function(tableName) {
      $state.go('dashboard.table', {tableName : tableName});
    };
  };

  app.controller('navigationController', navigationController);


})(angular);
