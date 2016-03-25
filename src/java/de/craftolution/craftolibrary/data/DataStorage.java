package de.craftolution.craftolibrary.data;

import java.util.Optional;

import com.google.common.collect.MutableClassToInstanceMap;

import de.craftolution.craftolibrary.data.result.DataTransactionResult;
import de.craftolution.craftolibrary.data.result.DataTransactionResult.ResultType;

/**
 * TODO: Documentation
 *
 * @author Fear837
 * @since 26.02.2016
 */
public class DataStorage {

	final MutableClassToInstanceMap<Data> dataMap = MutableClassToInstanceMap.create();

	/** TODO: Documentation */
	public DataTransactionResult offer(final Data data) {
		/*Data oldData = */this.dataMap.get(data.getClass());
		final Data replacedData = this.dataMap.put(data.getClass(), data);
		return new DataTransactionResult(this, ResultType.SUCCESS, null, data, replacedData);
	}

	/** TODO: Documentation */
	public DataTransactionResult remove(final Class<? extends Data> data) {
		/*Data oldData = */this.dataMap.get(data.getClass());
		final Data removedData = this.dataMap.remove(data);
		return new DataTransactionResult(this, ResultType.SUCCESS, null, null, removedData);
	}

	/** TODO: Documentation */
	public <T extends Data> Optional<T> get(final Class<T> dataClass) {
		final Data data = this.dataMap.get(dataClass);
		if (data != null) { return Optional.of( this.cast(dataClass, data) ); }
		return Optional.empty();
	}

	@SuppressWarnings("unchecked")
	private <T extends Data> T cast(final Class<T> dataClass, final Data data) {
		return (T) data;
	}

}