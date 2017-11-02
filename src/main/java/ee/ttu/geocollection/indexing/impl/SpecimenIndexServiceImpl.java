package ee.ttu.geocollection.indexing.impl;

import ee.ttu.geocollection.domain.SortField;
import ee.ttu.geocollection.domain.SortingOrder;
import ee.ttu.geocollection.indexing.AbstractIndexingService;
import ee.ttu.geocollection.indexing.domain.DataType;
import ee.ttu.geocollection.indexing.domain.DocumentBuilder;
import ee.ttu.geocollection.indexing.domain.QueryParameters;
import ee.ttu.geocollection.indexing.technical.TechnicalIndexService;
import ee.ttu.geocollection.interop.api.AsynchService;
import ee.ttu.geocollection.interop.api.Response.ApiResponse;
import ee.ttu.geocollection.interop.api.specimen.pojo.SpecimenSearchCriteria;
import ee.ttu.geocollection.interop.api.specimen.service.SpecimenApiService;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.util.BytesRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

import static ee.ttu.geocollection.indexing.GlobalSearchConstants.ID_GROUPING;
import static ee.ttu.geocollection.interop.api.builder.ApiFields.*;
import static java.util.stream.Collectors.toList;

@Service
public class SpecimenIndexServiceImpl extends AbstractIndexingService<SpecimenSearchCriteria> {
    @Autowired
    private DirectoryReader specimenDirectoryReader;
    @Autowired
    private IndexWriter specimenDirectoryWriter;
    @Autowired
    private TechnicalIndexService technicalIndexService;
    @Autowired
    private SpecimenApiService specimenApiService;
    @Autowired
    private AsynchService asynchService;

    @Override
    protected void updateIndex() {
        SpecimenSearchCriteria updateSearchCriteria = new SpecimenSearchCriteria();
        updateSearchCriteria.setSortField(new SortField(DATE_CHANGED, SortingOrder.DESCENDING));
        updateOldIndices(
                updateSearchCriteria,
                (searchCriteria) -> specimenApiService.findSpecimensForIndex(searchCriteria),
                specimenDirectoryWriter,
                specimenDirectoryReader,
                technicalIndexService);
    }

    @Override
    protected void createIndex() {
        SpecimenSearchCriteria createSearchCriteria = new SpecimenSearchCriteria();
        createSearchCriteria.setSortField(new SortField(ID, SortingOrder.DESCENDING));
        createMissingIndices(
                createSearchCriteria,
                (searchCriteria) -> specimenApiService.findSpecimensForIndex(searchCriteria),
                specimenDirectoryWriter,
                specimenDirectoryReader,
                technicalIndexService);
    }

    @Override
    public ApiResponse searchInIndex(String value) {
        Collection<Document> documents = technicalIndexService.searchInIndex(
                QueryParameters.params()
                        .queryValue(value)
                        .appendParameter(ID, DataType.NUMERIC)
                        .appendParameter(SPECIMEN_NR, DataType.STRING)
                        .appendParameter(SPECIMEN_ID, DataType.STRING)
                        .appendParameter(CLASSIFICATION__CLASS_FIELD, DataType.TEXT)
                        .appendParameter(CLASSIFICATION__CLASS_EN, DataType.TEXT)
                        .appendParameter(SPECIMENIDENTIFICATION__NAME, DataType.TEXT)
                        .appendParameter(SPECIMENIDENTIFICATION__TAXON__TAXON, DataType.TEXT),
                specimenDirectoryReader);
        return documents.isEmpty() ? new ApiResponse() : findSpecimensByIds(documents);
    }

    private ApiResponse findSpecimensByIds(Collection<Document> documents) {
        return specimenApiService.findSpecimensByIds(
                documents.stream()
                        .map(document -> document.get(ID))
                        .collect(toList()));
    }

    @Override
    protected Document buildDocument(Map<String, Object> entry) {
        Document document = DocumentBuilder.aDocument()
                .targetEntry(entry)
                .withField(ID, StringField.TYPE_STORED)
                .withField(SPECIMEN_NR, StringField.TYPE_NOT_STORED)
                .withField(SPECIMEN_ID, StringField.TYPE_NOT_STORED)
                .withField(CLASSIFICATION__CLASS_FIELD, TextField.TYPE_NOT_STORED)
                .withField(CLASSIFICATION__CLASS_EN, TextField.TYPE_NOT_STORED)
                .withField(SPECIMENIDENTIFICATION__NAME, TextField.TYPE_NOT_STORED)
                .withField(SPECIMENIDENTIFICATION__TAXON__TAXON, TextField.TYPE_NOT_STORED)
                .withField(DATE_CHANGED, StringField.TYPE_STORED)
                .build();
        document.add(new SortedDocValuesField(ID_GROUPING, new BytesRef(entry.get(ID).toString().getBytes())));

        return document;
    }
}
