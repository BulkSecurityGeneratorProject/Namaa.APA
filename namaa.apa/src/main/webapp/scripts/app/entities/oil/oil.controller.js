'use strict';

angular.module('namaaApaApp')
    .controller('OilController', function ($scope, Oil) {
        $scope.oils = [];
        $scope.loadAll = function() {
            Oil.query(function(result) {
               $scope.oils = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Oil.get({id: id}, function(result) {
                $scope.oil = result;
                $('#deleteOilConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Oil.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOilConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.oil = {sesame: null, safflower: null, radish: null, id: null};
        };
    });
