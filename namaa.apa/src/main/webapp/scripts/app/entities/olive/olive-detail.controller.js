'use strict';

angular.module('namaaApaApp')
    .controller('OliveDetailController', function ($scope, $rootScope, $stateParams, entity, Olive, User) {
        $scope.olive = entity;
        $scope.load = function (id) {
            Olive.get({id: id}, function(result) {
                $scope.olive = result;
            });
        };
        $rootScope.$on('namaaApaApp:oliveUpdate', function(event, result) {
            $scope.olive = result;
        });
    });
