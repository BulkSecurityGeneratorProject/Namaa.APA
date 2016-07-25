'use strict';

angular.module('namaaApaApp')
    .controller('FruitController', function ($scope, Fruit) {
        $scope.fruits = [];
        $scope.loadAll = function() {
            Fruit.query(function(result) {
               $scope.fruits = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Fruit.get({id: id}, function(result) {
                $scope.fruit = result;
                $('#deleteFruitConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Fruit.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteFruitConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.fruit = {grapes: null, dates: null, watered: null, nonWatered: null,id: null};
};
    });
