(function(angular) {
    var app = angular.module('app');

    app.factory('UtilsService', function() {
      return {
          capitalize : function(uncapitalized) {
            uncapitalized = uncapitalized.replace('_', ' ').toLowerCase();

            var capital  =uncapitalized.substring(0, 1).toUpperCase();
            var capitalized = capital + uncapitalized.substring(1);
            return capitalized;
          }
      };
    });
})(angular);
