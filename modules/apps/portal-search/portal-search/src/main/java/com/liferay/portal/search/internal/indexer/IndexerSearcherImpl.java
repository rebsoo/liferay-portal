/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SearchResultPermissionFilter;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterFactory;
import com.liferay.portal.kernel.search.SearchResultPermissionFilterSearcher;
import com.liferay.portal.kernel.search.hits.HitsProcessorRegistry;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.search.indexer.IndexerPermissionPostFilter;
import com.liferay.portal.search.indexer.IndexerQueryBuilder;
import com.liferay.portal.search.indexer.IndexerSearcher;
import com.liferay.portal.search.internal.searcher.IndexSearcherHelper;
import com.liferay.portal.search.spi.model.query.contributor.QueryConfigContributor;
import com.liferay.portal.search.spi.model.query.contributor.helper.QueryConfigContributorHelper;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

/**
 * @author Michael C. Han
 */
public class IndexerSearcherImpl<T extends BaseModel<?>>
	implements IndexerSearcher {

	public IndexerSearcherImpl(
		ModelSearchSettings modelSearchSettings,
		Iterable<QueryConfigContributor> modelQueryConfigContributors,
		IndexerPermissionPostFilter indexerPermissionPostFilter,
		IndexerQueryBuilder indexerQueryBuilder,
		HitsProcessorRegistry hitsProcessorRegistry,
		IndexSearcherHelper indexSearcherHelper,
		Iterable<QueryConfigContributor> queryConfigContributors,
		SearchResultPermissionFilterFactory
			searchResultPermissionFilterFactory) {

		_modelSearchSettings = modelSearchSettings;
		_modelQueryConfigContributors = modelQueryConfigContributors;
		_indexerPermissionPostFilter = indexerPermissionPostFilter;
		_indexerQueryBuilder = indexerQueryBuilder;
		_hitsProcessorRegistry = hitsProcessorRegistry;
		_indexSearcherHelper = indexSearcherHelper;
		_queryConfigContributors = queryConfigContributors;
		_searchResultPermissionFilterFactory =
			searchResultPermissionFilterFactory;
	}

	@Override
	public Hits search(SearchContext searchContext) {
		QueryConfigContributorHelper queryConfigContributorHelper =
			new QueryConfigContributorHelper() {

				@Override
				public String[] getDefaultSelectedFieldNames() {
					return _modelSearchSettings.getDefaultSelectedFieldNames();
				}

				@Override
				public String[] getDefaultSelectedLocalizedFieldNames() {
					return _modelSearchSettings.
						getDefaultSelectedLocalizedFieldNames();
				}

				@Override
				public boolean isSelectAllLocales() {
					return _modelSearchSettings.isSelectAllLocales();
				}

			};

		_queryConfigContributors.forEach(
			queryConfigContributor ->
				queryConfigContributor.contributeQueryConfigurations(
					searchContext, queryConfigContributorHelper));

		_modelQueryConfigContributors.forEach(
			modelQueryConfigContributor ->
				modelQueryConfigContributor.contributeQueryConfigurations(
					searchContext, queryConfigContributorHelper));

		Hits hits = _search(searchContext);

		processHits(searchContext, hits);

		return hits;
	}

	@Override
	public long searchCount(SearchContext searchContext) {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if ((permissionChecker != null) &&
			_indexerPermissionPostFilter.isPermissionAware()) {

			Hits hits = search(searchContext);

			return hits.getLength();
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setHitsProcessingEnabled(false);
		queryConfig.setScoreEnabled(false);
		queryConfig.setQueryIndexingEnabled(false);
		queryConfig.setQuerySuggestionEnabled(false);

		searchContext.setSearchEngineId(
			_modelSearchSettings.getSearchEngineId());

		BooleanQuery fullQuery = _indexerQueryBuilder.getQuery(searchContext);

		fullQuery.setQueryConfig(queryConfig);

		return _indexSearcherHelper.searchCount(searchContext, fullQuery);
	}

	protected Hits doSearch(SearchContext searchContext) {
		searchContext.setSearchEngineId(
			_modelSearchSettings.getSearchEngineId());

		Query fullQuery = _indexerQueryBuilder.getQuery(searchContext);

		fullQuery.setQueryConfig(searchContext.getQueryConfig());

		return _indexSearcherHelper.search(searchContext, fullQuery);
	}

	protected boolean isUseSearchResultPermissionFilter() {
		if (_indexerPermissionPostFilter.isPermissionAware() &&
			!_modelSearchSettings.isSearchResultPermissionFilterSuppressed()) {

			return true;
		}

		return false;
	}

	protected void processHits(SearchContext searchContext, Hits hits) {
		try {
			_hitsProcessorRegistry.process(searchContext, hits);
		}
		catch (SearchException searchException) {
			throw new RuntimeException(searchException);
		}
	}

	private SearchResultPermissionFilter _getSearchResultPermissionFilter(
		SearchContext searchContext,
		SearchResultPermissionFilterSearcher
			searchResultPermissionFilterSearcher) {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			return null;
		}

		if (searchContext.getUserId() == 0) {
			searchContext.setUserId(permissionChecker.getUserId());
		}

		return _searchResultPermissionFilterFactory.create(
			searchResultPermissionFilterSearcher, permissionChecker);
	}

	private Hits _search(SearchContext searchContext) {
		try {
			if (isUseSearchResultPermissionFilter()) {
				SearchResultPermissionFilter searchResultPermissionFilter =
					_getSearchResultPermissionFilter(
						searchContext, this::doSearch);

				if (searchResultPermissionFilter != null) {
					return searchResultPermissionFilter.search(searchContext);
				}
			}

			return doSearch(searchContext);
		}
		catch (SearchException searchException) {
			throw new RuntimeException(searchException);
		}
	}

	private final HitsProcessorRegistry _hitsProcessorRegistry;
	private final IndexerPermissionPostFilter _indexerPermissionPostFilter;
	private final IndexerQueryBuilder _indexerQueryBuilder;
	private final IndexSearcherHelper _indexSearcherHelper;
	private final Iterable<QueryConfigContributor>
		_modelQueryConfigContributors;
	private final ModelSearchSettings _modelSearchSettings;
	private final Iterable<QueryConfigContributor> _queryConfigContributors;
	private final SearchResultPermissionFilterFactory
		_searchResultPermissionFilterFactory;

}