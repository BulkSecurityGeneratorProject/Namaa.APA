'use strict';

angular.module('namaaApaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('oil', {
                parent: 'entity',
                url: '/oils',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'namaaApaApp.oil.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/oil/oils.html',
                        controller: 'OilController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oil');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('oil.detail', {
                parent: 'entity',
                url: '/oil/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'namaaApaApp.oil.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/oil/oil-detail.html',
                        controller: 'OilDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('oil');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Oil', function($stateParams, Oil) {
                        return Oil.get({id : $stateParams.id});
                    }]
                }
            })
            .state('oil.new', {
                parent: 'oil',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/oil/oil-dialog.html',
                        controller: 'OilDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {sesame: null, safflower: null, radish: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('oil', null, { reload: true });
                    }, function() {
                        $state.go('oil');
                    })
                }]
            })
            .state('oil.edit', {
                parent: 'oil',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/oil/oil-dialog.html',
                        controller: 'OilDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Oil', function(Oil) {
                                return Oil.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('oil', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
