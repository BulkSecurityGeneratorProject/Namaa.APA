'use strict';

angular.module('namaaApaApp')
    .controller('OilDetailController', function ($scope, $rootScope, $stateParams, entity, Oil, User) {
        $scope.oil = entity;
        $scope.load = function (id) {
            Oil.get({id: id}, function(result) {
                $scope.oil = result;
            });
        };
        $rootScope.$on('namaaApaApp:oilUpdate', function(event, result) {
            $scope.oil = result;
        });
    });
