(function(angular) {

    var app = angular.module('app');

    app.factory('DatabaseService', function($http) {
        return {
            getTablesName: function(callback) {
                $http.get('/api/database/tables').then(function(result) {
                    callback(result);
                });
            }
        };
    });
})(angular);
