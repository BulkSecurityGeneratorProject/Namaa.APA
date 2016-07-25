'use strict';

angular.module('namaaApaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fruit', {
                parent: 'entity',
                url: '/fruits',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'namaaApaApp.fruit.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fruit/fruits.html',
                        controller: 'FruitController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fruit');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('fruit.detail', {
                parent: 'entity',
                url: '/fruit/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'namaaApaApp.fruit.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fruit/fruit-detail.html',
                        controller: 'FruitDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('fruit');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Fruit', function($stateParams, Fruit) {
                        return Fruit.get({id : $stateParams.id});
                    }]
                }
            })
            .state('fruit.new', {
                parent: 'fruit',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fruit/fruit-dialog.html',
                        controller: 'FruitDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {grapes: null, dates: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fruit', null, { reload: true });
                    }, function() {
                        $state.go('fruit');
                    })
                }]
            })
            .state('fruit.edit', {
                parent: 'fruit',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fruit/fruit-dialog.html',
                        controller: 'FruitDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Fruit', function(Fruit) {
                                return Fruit.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fruit', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
