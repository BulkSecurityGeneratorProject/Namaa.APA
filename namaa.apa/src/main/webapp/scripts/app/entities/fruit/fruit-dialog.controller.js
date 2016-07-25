'use strict';

angular.module('namaaApaApp').
    controller('FruitDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Fruit', 'User','Principal',
        function($scope, $stateParams, $modalInstance, entity, Fruit, User, Principal) {

        $scope.fruit = entity;
            $scope.fruit.watered=0.0;
            $scope.fruit.nonWatered=0.0;
            $scope.fruit.dates=0.0;
            $scope.fruit.grapes=0.0;
            $scope.users = User.query();
        $scope.load = function(id) {
            Fruit.get({id : id}, function(result) {
                $scope.fruit = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('namaaApaApp:fruitUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if($scope.fruit.dates+$scope.fruit.grapes == $scope.fruit.watered+$scope.fruit.nonWatered) {
                if ($scope.fruit.id != null) {
                    Fruit.update($scope.fruit, onSaveFinished);
                } else {
                    Fruit.save($scope.fruit, onSaveFinished);
                }
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
