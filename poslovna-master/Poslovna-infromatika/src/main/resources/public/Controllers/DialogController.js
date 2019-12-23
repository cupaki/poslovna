(function(angular) {

    var app = angular.module('app');

    var DialogController = function($scope, tableName, TableService, $uibModalInstance, $uibModal, $log, PickUpService, UtilsService, SelectedService, StateService) {

        /*   	var nazivTabeleBaza = tableName;
           	var r = nazivTabeleBaza.replace(' ','_');
           	var n = r.toUpperCase();
           	console.log(n + ' NAZIV');*/

        TableService.getLinkedTable(tableName, function(result) {
            $scope.linkedList = result.data;
        });

        $scope.insertingValue = [];
        $scope.tableName = tableName;

        var sizeToFit = function() {
            gridOptions.api.sizeColumnsToFit();
        };
        var initData = function() {
            for (var i = 0; i < $scope.insertingValue.length; i++) {
                $scope.selectedRowData.push({
                  columnName: $scope.insertingValue[i].columnName,
                  columnValue: '',
                  columnType: $scope.insertingValue[i].columnType,
                  primaryKey: $scope.insertingValue[i].primaryKey,
                  forgienKey: $scope.insertingValue[i].forgienKey,
                  lookUp : $scope.insertingValue[i].lookUp
                });
            }
        };

        $scope.open = function(tableName) {

            var modalInstance = $uibModal.open({
                templateUrl: '/Templates/dialog.html',
                controller: 'DialogController',
                windowClass: 'xx-dialog',
                resolve: {
                    tableName: function() {
                        return tableName;
                    },
                    items: function() {
                        return $scope.items;
                    }
                }
            });

            modalInstance.result.then(function(selectedItem) {
                $scope.selected = selectedItem;
                initData();
                // postavljanje pickUp vrednosti na ng-model
                var vrednostKolone = PickUpService.getVrednost();
                var nazivKolone = PickUpService.getColumn();
                var lookUpcolumn = PickUpService.getLookColumn();
                var lookUpValue = PickUpService.getLookUpValue();
                for (var i = 0; i < $scope.selectedRowData.length; i++) {
                    if ($scope.selectedRowData[i].columnName == nazivKolone) {
                        $scope.selectedRowData[i].columnValue = vrednostKolone;
                    }
                    if ($scope.selectedRowData[i].columnName == lookUpcolumn) {
                        $scope.selectedRowData[i].columnValue = lookUpValue;
                    }
                }



            }, function() {
                $log.info('Modal dismissed at: ' + new Date());



            });
        };

        var columnDefs = [];

        var gridOptions = {
            columnDefs: columnDefs,
            rowSelection: 'multiple',
            onSelectionChanged: onSelectionChanged
        };
        $scope.selectedRowData = [];

        function onSelectionChanged() {
            $scope.selectedRowData = [];
            var selectedRows = gridOptions.api.getSelectedRows();
            var selectedRowsString = '';


            for (var i = 0; i < $scope.insertingValue.length; i++) {
                if ($scope.insertingValue[i].columnType !== 'datetime') {
                    var obj = selectedRows[0][$scope.insertingValue[i].columnName];
                    $scope.selectedRowData.push({
                        columnName: $scope.insertingValue[i].columnName,
                        columnValue: obj,
                        columnType: $scope.insertingValue[i].columnType,
                        primaryKey: $scope.insertingValue[i].primaryKey,
                        forgienKey: $scope.insertingValue[i].forgienKey,
                        lookUp : $scope.insertingValue[i].lookUp
                    });
                } else {
                    var date = new Date(selectedRows[0][$scope.insertingValue[i].columnName]);
                    $scope.selectedRowData.push({
                        columnName: $scope.insertingValue[i].columnName,
                        columnValue: date,
                        columnType: $scope.insertingValue[i].columnType,
                        primaryKey: $scope.insertingValue[i].primaryKey,
                        forgienKey: $scope.insertingValue[i].forgienKey,
                        lookUp : $scope.insertingValue[i].lookUp
                    });
                }
            }
            // $scope.$apply();
            $scope.$evalAsync();

        }
        var url = '/api/table/' + $scope.tableName;

        angular.element(document).ready(function() {
            TableService.getColumnsForTable($scope.tableName, function(tableColumns) {


                $scope.tableColumns = tableColumns;
                for (var i = 0; i < tableColumns.length; i++) {
                	 columnDefs.push({
                         headerName: UtilsService.capitalize(tableColumns[i].name),
                         field: tableColumns[i].name
                     });
                     if (tableColumns[i].lookupField !== null) {
                         columnDefs.push({
                             headerName: UtilsService.capitalize(tableColumns[i].lookupField.name),
                             field: tableColumns[i].lookupField.name
                         });
                     }
                    $scope.insertingValue.push({
                        columnName: tableColumns[i].name,
                        columnType: tableColumns[i].type,
                        columnValue: '',
                        tableId: tableColumns[i].tableColumnId,
                        primaryKey: tableColumns[i].primaryKey,
                        forgienKey: tableColumns[i].forgienKey,
                        lookupField: tableColumns[i].lookupField,
                        fkTable: tableColumns[i].fkTable,
                        lookUp : tableColumns[i].lookUp
                    });
                    if (tableColumns[i].lookupField !== null) {
                        $scope.insertingValue.push({
                            columnName: tableColumns[i].lookupField.name,
                            columnType: tableColumns[i].lookupField.type,
                            columnValue: '',
                            tableId: tableColumns[i].lookupField.tableColumnId,
                            primaryKey: tableColumns[i].lookupField.primaryKey,
                            forgienKey: tableColumns[i].lookupField.forgienKey,
                            lookupField: tableColumns[i].lookupField.lookupField,
                            fkTable: tableColumns[i].fkTable,
                            lookUp : tableColumns[i].lookupField.lookUp
                        });
                    }
                    $scope.selectedRowData.push({
                        columnName: tableColumns[i].name,
                        columnType: tableColumns[i].type,
                        columnValue: '',
                        tableId: tableColumns[i].tableColumnId,
                        primaryKey: tableColumns[i].primaryKey,
                        forgienKey: tableColumns[i].forgienKey,
                        lookupField: tableColumns[i].lookupField,
                        lookUp : tableColumns[i].lookUp
                    });
                    if (tableColumns[i].lookupField !== null) {
                        $scope.selectedRowData.push({
                            columnName: tableColumns[i].lookupField.name,
                            columnType: tableColumns[i].lookupField.type,
                            columnValue: '',
                            tableId: tableColumns[i].lookupField.tableColumnId,
                            primaryKey: tableColumns[i].lookupField.primaryKey,
                            forgienKey: tableColumns[i].lookupField.forgienKey,
                            lookupField: tableColumns[i].lookupField.lookupField,
                            lookUp: tableColumns[i].lookupField.lookUp
                        });
                    }
                }
                var gridDiv = document.querySelector('#dialogGrid');
                new agGrid.Grid(gridDiv, gridOptions);
                TableService.getDataForTable($scope.tableName, function(tableData) {
                    var rows = [];
                    for (var i = 0; i < tableData.length; i++) {
                        var row = {};
                        for (var j = 0; j < $scope.insertingValue.length; j++) {
                            if ($scope.insertingValue[j].lookupField === null) {
                                row[$scope.insertingValue[j].columnName] = tableData[i][j];
                            } else {
                                row[$scope.insertingValue[j].columnName] = tableData[i][j];
                                row[$scope.insertingValue[j].lookupField.name] = tableData[i][j];
                            }
                        }
                        rows.push(row);
                    }

                    gridOptions.api.setRowData(rows);

                    //$scope.goFirst();

                    sizeToFit();

                });

            });

        });

        var clearSelection = function() {
          $scope.selectedRowData = [];
        };
        SelectedService.clearDialogSelection = clearSelection;

        $scope.add = function() {
            var state = StateService.getState();
            var states = StateService.getStates();
            if (state === states.EDIT) {
                StateService.setState(states.ADD);
            } else if (state === states.SEARCH) {
                StateService.setState(states.ADD);
            }
            SelectedService.clearDialogSelection();
            $scope.$evalAsync();

        };

        $scope.goFirst = function() {
            gridOptions.api.forEachNode(function(node) {

                if (node.id == 0) {
                    node.setSelected(true, true);
                }
            });
        }


        $scope.goLast = function() {

            var lastRow = gridOptions.api.getModel().rowsToDisplay.length;
            gridOptions.api.forEachNode(function(node) {


                if (node.id == lastRow - 1) {
                    node.setSelected(true, true);
                }
            });
        }

        $scope.goNext = function() {

            var sledeci = 0;

            gridOptions.api.forEachNode(function(node) {
                if (node.isSelected()) {
                    sledeci = node.id + 1;
                }
            });

            gridOptions.api.forEachNode(function(node) {
                if (node.id == sledeci) {
                    node.setSelected(true, true);
                }
            });
        }

        $scope.goPrevious = function() {

            var prethodni = 0;

            gridOptions.api.forEachNode(function(node) {
                if (node.isSelected()) {
                    sledeci = node.id - 1;
                }
            });
            gridOptions.api.forEachNode(function(node) {
                if (node.id == sledeci) {
                    node.setSelected(true, true);
                }
            });
        };


        $scope.pickUp = function() {

            var selectedRows = gridOptions.api.getSelectedRows();
            //ubaci naziv!
            TableService.getPrimaryKeyName(tableName, function(result) {
                //na ng-model odgovarajuci kacimo vrednost
                PickUpService.setVrednost(selectedRows[0][result.data[0]]);
                PickUpService.setColumn([result.data[0]]);

                var nazivPolja = [result.data[0]];
                var vrednostPolja = selectedRows[0][result.data[0]];


                if (result.data[0] === 'ID_PREDUZECA') {
                    var lookupColumn = 'NAZIV_PREDUZECA';
                    PickUpService.setLookUpColumn(lookupColumn);
                    PickUpService.setLookUpValue(selectedRows[0][lookupColumn]);
                }
                if (result.data[0] === 'ID_PARTNERA') {
                    var lookupColumn = 'NAZIV_PARTNERA';
                    PickUpService.setLookUpColumn(lookupColumn);
                    PickUpService.setLookUpValue(selectedRows[0][lookupColumn]);
                }
                if (result.data[0] === 'ID_GODINE') {
                    var lookupColumn = 'GODINA';
                    PickUpService.setLookUpColumn(lookupColumn);
                    PickUpService.setLookUpValue(selectedRows[0][lookupColumn]);
                }
                if (result.data[0] === 'ID_PDV') {
                    var lookupColumn = 'NAZIV_PDV_A';
                    PickUpService.setLookUpColumn(lookupColumn);
                    PickUpService.setLookUpValue(selectedRows[0][lookupColumn]);
                }
                if (result.data[0] === 'ID_JEDINICE_MERE') {
                    var lookupColumn = 'NAZIV_JEDINICE_MERE';

                    PickUpService.setLookUpColumn(lookupColumn);
                    PickUpService.setLookUpValue(selectedRows[0][lookupColumn]);
                }
                if (result.data[0] === 'ID_GRUPE') {
                    var lookupColumn = 'NAZIV_GRUPE';
                    PickUpService.setLookUpColumn(lookupColumn);
                    PickUpService.setLookUpValue(selectedRows[0][lookupColumn]);
                }
                if (result.data[0] === 'ID_PROIZVODA') {
                    var lookupColumn = 'NAZIV_PROIZVODA';
                    PickUpService.setLookUpColumn(lookupColumn);
                    PickUpService.setLookUpValue(selectedRows[0][lookupColumn]);
                }
                if (result.data[0] === 'ID_CENOVNIKA') {
                    var lookupColumn = 'DATUM_VAZENA';
                    PickUpService.setLookUpColumn(lookupColumn);
                    PickUpService.setLookUpValue(selectedRows[0][lookupColumn]);
                }
                if (result.data[0] === 'ID_FAKTURE') {
                    var lookupColumn = 'BROJ_FAKTURE';
                    PickUpService.setLookUpColumn(lookupColumn);
                    PickUpService.setLookUpValue(selectedRows[0][lookupColumn]);
                }
                //DODAO
                if(tableName === 'PROIZVOD'){
                  TableService.getJosOpisnihPolja(nazivPolja, vrednostPolja, function(result) {
                    PickUpService.setSkracenica(result.data[0]);
                    PickUpService.setIdJedMere(result.data[1]);
                  });
                }
                if (tableName === 'STOPA_PDV_A') {
                  console.log('rowsss');
                  console.log(selectedRows['STOPA']);
                  PickUpService.setStopaPdv(selectedRows[0]['STOPA']);
                  console.log('slecetovani pdv stopa: ' + PickUpService.getStopaPdv());
                }
                setTimeout(function () {
                  $uibModalInstance.close('Odabrao vrednost');
                }, 500);

            });

        };

        $scope.capitalize = function(str) {
            return UtilsService.capitalize(str);
        };

    };

    app.controller('DialogController', DialogController);
})(angular);
