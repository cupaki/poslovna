(function(angular) {

  var app = angular.module('app');

  app.service('SelectedService', function() {
    var that = this;
    var selectedRowData = [];

    this.getSelectedRow = function() {
      return that.selectedRowData;
    };
    this.setSelectedRow = function(selected) {
      console.log('setovaop');
      console.log(selected);
      that.selectedRowData = selected;
    };
    this.clearSelection = {};
    this.clearDialogSelection = {};
  });
})(angular);
