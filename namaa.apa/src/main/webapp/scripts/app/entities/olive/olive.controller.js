'use strict';

angular.module('namaaApaApp')
    .controller('OliveController', function ($scope, Olive) {
        $scope.olives = [];
        $scope.loadAll = function() {
            Olive.query(function(result) {
               $scope.olives = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Olive.get({id: id}, function(result) {
                $scope.olive = result;
                $('#deleteOliveConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Olive.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOliveConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.olive = {oliveOil: null, id: null};
        };
    });
