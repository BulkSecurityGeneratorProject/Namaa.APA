'use strict';

angular.module('namaaApaApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('olive', {
                parent: 'entity',
                url: '/olives',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'namaaApaApp.olive.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/olive/olives.html',
                        controller: 'OliveController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('olive');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('olive.detail', {
                parent: 'entity',
                url: '/olive/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'namaaApaApp.olive.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/olive/olive-detail.html',
                        controller: 'OliveDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('olive');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Olive', function($stateParams, Olive) {
                        return Olive.get({id : $stateParams.id});
                    }]
                }
            })
            .state('olive.new', {
                parent: 'olive',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/olive/olive-dialog.html',
                        controller: 'OliveDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {oliveOil: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('olive', null, { reload: true });
                    }, function() {
                        $state.go('olive');
                    })
                }]
            })
            .state('olive.edit', {
                parent: 'olive',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/olive/olive-dialog.html',
                        controller: 'OliveDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Olive', function(Olive) {
                                return Olive.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('olive', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
